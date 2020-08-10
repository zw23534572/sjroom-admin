package github.sjroom.core.mybatis.mapper;

import github.sjroom.core.code.IErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务 code，需要配置 code 前缀，业务只需要关心code中间5位
 *
 * <p>
 * 业务标识：2位，开发人员按照业务模块定义，例如：(01)SKU。 业务错误码：3位，开发人员自己定义。
 * </p>
 *
 * @author manson.zhou
 * @version 1.0.0
 * @since 2019-05-27 08:34
 */
@Getter
@AllArgsConstructor
public enum ApiCode implements IErrorCode {
	COMMON_VALIDATION_INCONSISTENT_LENGTH("00105", "长度为4至32个字符(包括字母、数字、下划线){0}"),

	COMMON_UPDATE_OPERATE("00101", "仅启用或已禁用的状态方可操作"),
	COMMON_UPDATE_ENABLE_ING("00102", "仅待启用的数据可进行操作"),
	COMMON_UPDATE_PRIMARY_KEY_IS_NOT_NULL("00103", "{0}Id不能为空"),
	COMMON_UPDATE_STATUS_INIT_OPERATE("00106", "仅待启用的数据可进行编辑"),
	COMMON_ENABLE_OPERATE("00104", "仅新建及已禁用状态下数据可进行启用,请确认数据状态后，再操作启用"),
	COMMON_PROHIBIT_OPERATE("00105", "仅已启用状态可禁用操作，请确认数据状态后，再操作禁用"),
	COMMON_REMOVE_OPERATE("00106", "仅新建状态才可删除，请确认数据状态后，再操作删除"),

	COMPANY_IS_NOT_EXIT("00200", "该公司不存在"),
	COMPANY_IS_NOT_NULL("00210", "该公司不能为空"),

	DEPT_IS_NOT_EXIT("00701", "该部门不存在"),
	DEPT_IS_NOT_NULL("00702", "该部门不能为空"),
	DEPT_NAME_IS_EXIT("00703", "该部门名称已存在，请重新输入"),
	DEPT_ADD_PARENT_ID_IS_ZERO("00704", "部门最顶级不能被禁用"),
	DEPT_ADD_PARENT_ID_IS_PROHIBIT("00705", "新增的部门不能为最顶级"),
	DEPT_PARENT_ID_IS_UN_NABLE("00706", "上级部门待启用与禁用状态，不能操作"),
	DEPT_PARENT_ID_IS_UN_FIND("00707", "上级部门未能找到:{0}"),
	DEPT_PARENT_ID_IS_TYPE_EQUAL("00708", "与选择的上级部门类型要相同"),
	DEPT_ID_NOT_EXIST("00712", "部门信息不存在"),
	DEPT_PROHIBIT("00714", "您选中的部门下有关联的员工，请将所有下属员工禁用或删除后再禁用部门"),
	DEPT_SUB_PROHIBIT("00715", "您选中的部门:{0}的子部门:{1}为启用状态"),
	DEPT_PARENT_IS_PROHIBIT("00615", "部门{0}的上级部门{1}被禁用"),

	EMPLOYEE_NUMBER_NO_IS_NOT_NULL("00610", "工号信息异常，请联系系统管理员"),
	EMPLOYEE_NUMBER_NO_IS_EXIST_NAME("00611", "该工号已存在,请重新填写!"),
	EMPLOYEE_NAME_ZH_IS_NOT_NULL("00620", "请填写姓名!"),
	EMPLOYEE_NAME_ZH_ERROR("00621", "姓名只能由中文组成!"),
	EMPLOYEE_NAME_EN_IS_NOT_NULL("00630", "请填写英文名!"),
	EMPLOYEE_NAME_EN_ERROR("00631", "英文名只能由字母和.组成!"),
	EMPLOYEE_EMAIL_IS_NOT_NULL("00640", "请填写邮箱!"),
	EMPLOYEE_EMAIL_ILLEGAL_SUFFIX("00642", "非法的邮箱后缀{0}"),
	EMPLOYEE_EMAIL_ILLEGAL_FORMAT("00643", "非法的邮箱格式{0}"),
	EMPLOYEE_MOBILE_IS_NOT_NULL("00650", "请填写手机号码!"),
	EMPLOYEE_MOBILE_INCONSISTENT_LENGTH("00651", "请输入正确的手机号码{0}"),

	EMPLOYEE_MOBILE_ERROR("00651", "手机号码为11位数字!"),
	EMPLOYEE_BIRTHDAY_IS_NOT_NULL("00660", "请选择生日!"),
	EMPLOYEE_INDUCTION_DATE_IS_NOT_NULL("00670", "请选择入职时间!"),
	EMPLOYEE_COMPANY_ID_IS_NOT_NULL("00680", "请选择公司!"),
	EMPLOYEE_DEPT_ID_IS_NOT_NULL("00690", "请选择部门!"),
	EMPLOYEE_ROLE_IS_NOT_NULL("00600", "业务角色{0}不存在"),
	EMPLOYEE_ROLE_NOT_OWNER_ERROR("00601", "没有所属角色{0}的权限"),
	EMPLOYEE_ROLE_DISTINCT_MENU_ERROR("00602", "所选业务角色{0}之间权限有交叉，请重新选择业务角色"),

	EMPLOYEE_FILE_FORMAT("00710", "文件后缀限定为xls,xlsx"),
	EMPLOYEE_FILE_ERROR_FORMAT("00720", "文件内容错误,请重新输入.");
	private String code;
	private String msg;
}
