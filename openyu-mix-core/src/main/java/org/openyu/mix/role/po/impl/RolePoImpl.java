package org.openyu.mix.role.po.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Store;
import org.openyu.mix.flutter.po.supporter.FlutterPoSupporter;
import org.openyu.mix.manor.po.bridge.ManorInfoBridge;
import org.openyu.mix.manor.vo.ManorInfo;
import org.openyu.mix.manor.vo.impl.ManorInfoImpl;
import org.openyu.mix.role.po.RolePo;
import org.openyu.mix.role.po.bridge.BagInfoBridge;
import org.openyu.mix.role.vo.BagInfo;
import org.openyu.mix.role.vo.impl.BagInfoImpl;
import org.openyu.mix.sasang.po.bridge.SasangInfoBridge;
import org.openyu.mix.sasang.vo.SasangInfo;
import org.openyu.mix.sasang.vo.impl.SasangInfoImpl;
import org.openyu.mix.train.po.bridge.TrainInfoBridge;
import org.openyu.mix.train.vo.TrainInfo;
import org.openyu.mix.train.vo.impl.TrainInfoImpl;
import org.openyu.mix.treasure.po.bridge.TreasureInfoBridge;
import org.openyu.mix.treasure.vo.TreasureInfo;
import org.openyu.mix.treasure.vo.impl.TreasureInfoImpl;
import org.openyu.mix.wuxing.po.bridge.WuxingInfoBridge;
import org.openyu.mix.wuxing.vo.WuxingInfo;
import org.openyu.mix.wuxing.vo.impl.WuxingInfoImpl;

//--------------------------------------------------
//hibernate
//--------------------------------------------------
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "role", indexes = { @Index(name = "idx_role_valid_id", columnList = "valid,id") })
@SequenceGenerator(name = "sg_role", sequenceName = "seq_role", allocationSize = 1)
// when use ehcache, config in ehcache.xml
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "org.openyu.mix.role.po.impl.RolePoImpl")
@Proxy(lazy = false)
// --------------------------------------------------
// search
// --------------------------------------------------
// @Indexed
public class RolePoImpl extends FlutterPoSupporter implements RolePo {

	private static final long serialVersionUID = 3612195473621975166L;

	private Long seq;

	/**
	 * 是否有效
	 */
	private Boolean valid;

	/**
	 * 帳號
	 */
	// private AccountPo account;

	private String accountId;

	/**
	 * 上線時間
	 */
	private Long enterTime;

	/**
	 * 離線時間
	 */
	private Long leaveTime;

	/**
	 * 取得接收器id
	 */
	private String acceptorId;

	/**
	 * 包包欄位
	 */
	private BagInfo bagInfo = new BagInfoImpl();

	// ---------------------------------------------------
	// 其他模組相關欄位
	// ---------------------------------------------------

	/**
	 * 四象欄位
	 */
	private SasangInfo sasangInfo = new SasangInfoImpl();

	/**
	 * 莊園欄位
	 */
	private ManorInfo manorInfo = new ManorInfoImpl();

	/**
	 * 祕寶欄位
	 */
	private TreasureInfo treasureInfo = new TreasureInfoImpl();

	/**
	 * 訓練欄位
	 */
	private TrainInfo trainInfo = new TrainInfoImpl();

	/**
	 * 五行欄位
	 */
	private WuxingInfo wuxingInfo = new WuxingInfoImpl();

	public RolePoImpl() {
	}

	@Id
	@Column(name = "seq")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "sg_role")
	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	@Column(name = "valid")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	// @ManyToOne(targetEntity = AccountPoImpl.class, cascade = CascadeType.ALL,
	// fetch = FetchType.EAGER)
	// @NotFound(action = NotFoundAction.IGNORE)
	// @JoinColumn(name = "account_id")
	// @IndexedEmbedded(targetElement = AccountPoImpl.class, depth = 1)
	// public AccountPo getAccount()
	// {
	// return account;
	// }
	//
	// public void setAccount(AccountPo account)
	// {
	// this.account = account;
	// }
	@Column(name = "account_id", length = 255)
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	@Column(name = "enter_time")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public Long getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(Long enterTime) {
		this.enterTime = enterTime;
	}

	@Column(name = "leave_time")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public Long getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(Long leaveTime) {
		this.leaveTime = leaveTime;
	}

	@Column(name = "acceptor_id", length = 30)
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public String getAcceptorId() {
		return acceptorId;
	}

	public void setAcceptorId(String acceptorId) {
		this.acceptorId = acceptorId;
	}

	@Column(name = "bag_info", length = 8192)
	@Type(type = "org.openyu.mix.role.po.usertype.BagInfoUserType")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = BagInfoBridge.class)
	public BagInfo getBagInfo() {
		return bagInfo;
	}

	public void setBagInfo(BagInfo bagInfo) {
		this.bagInfo = bagInfo;
	}

	@Column(name = "sasang_info", length = 1024)
	@Type(type = "org.openyu.mix.sasang.po.usertype.SasangInfoUserType")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = SasangInfoBridge.class)
	public SasangInfo getSasangInfo() {
		return sasangInfo;
	}

	public void setSasangInfo(SasangInfo sasangInfo) {
		this.sasangInfo = sasangInfo;
	}

	@Column(name = "manor_info", length = 2048)
	@Type(type = "org.openyu.mix.manor.po.usertype.ManorInfoUserType")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = ManorInfoBridge.class)
	public ManorInfo getManorInfo() {
		return manorInfo;
	}

	public void setManorInfo(ManorInfo manorInfo) {
		this.manorInfo = manorInfo;
	}

	@Column(name = "treasure_info", length = 1024)
	@Type(type = "org.openyu.mix.treasure.po.usertype.TreasureInfoUserType")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = TreasureInfoBridge.class)
	public TreasureInfo getTreasureInfo() {
		return treasureInfo;
	}

	public void setTreasureInfo(TreasureInfo treasureInfo) {
		this.treasureInfo = treasureInfo;
	}

	@Column(name = "train_info", length = 512)
	@Type(type = "org.openyu.mix.train.po.usertype.TrainInfoUserType")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = TrainInfoBridge.class)
	public TrainInfo getTrainInfo() {
		return trainInfo;
	}

	public void setTrainInfo(TrainInfo trainInfo) {
		this.trainInfo = trainInfo;
	}

	@Column(name = "wuxing_info", length = 1024)
	@Type(type = "org.openyu.mix.wuxing.po.usertype.WuxingInfoUserType")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = WuxingInfoBridge.class)
	public WuxingInfo getWuxingInfo() {
		return wuxingInfo;
	}

	public void setWuxingInfo(WuxingInfo wuxingInfo) {
		this.wuxingInfo = wuxingInfo;
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("valid", valid);
		// builder.append("account", (account != null ? account.getId() :
		// null));
		builder.append("accountId", accountId);
		builder.append("enterTime", enterTime);
		builder.append("leaveTime", leaveTime);
		//
		builder.append("bagInfo", bagInfo);
		builder.append("sasangInfo", sasangInfo);
		builder.append("manorInfo", manorInfo);
		builder.append("treasureInfo", treasureInfo);
		builder.append("trainInfo", trainInfo);
		builder.append("wuxingInfo", wuxingInfo);
		return builder.toString();
	}

	public Object clone() {
		RolePoImpl copy = null;
		copy = (RolePoImpl) super.clone();
		// copy.account = clone(copy);
		copy.bagInfo = clone(bagInfo);
		copy.sasangInfo = clone(sasangInfo);
		copy.manorInfo = clone(manorInfo);
		copy.treasureInfo = clone(treasureInfo);
		copy.trainInfo = clone(trainInfo);
		copy.wuxingInfo = clone(wuxingInfo);
		return copy;
	}
}
