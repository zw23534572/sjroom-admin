package github.sjroom.admin.controller;


import github.sjroom.admin.service.IDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <B>说明：数据字典表 控制器</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0
 * @since 2019-05-27 08:34
 */
@Slf4j
@Validated
@RestController
@Api("数据字典表 控制器")
@RequestMapping("api/dict")
public class DictController {

	@Autowired
	private IDictService dictService;


	@ApiOperation("refresh")
	@RequestMapping("refresh")
	public Long refresh() {

		dictService.echo();
		return 1l;
	}

}
