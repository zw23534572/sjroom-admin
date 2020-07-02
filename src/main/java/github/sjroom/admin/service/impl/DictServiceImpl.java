package github.sjroom.admin.service.impl;

import github.sjroom.admin.bean.entity.Dict;
import github.sjroom.admin.service.IDictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class DictServiceImpl implements IDictService {
	@Override
	public void echo() {
		log.info("manson test");
	}

	@Override
	public Map<Integer, Dict> findMap(Set<Long> ids) {
		log.info("DemoServiceImpl findMap ids:{}", ids);
		Dict dict = new Dict();
		dict.setDictId(1);
		dict.setDictName("test");

		Dict dict2 = new Dict();
		dict2.setDictId(2);
		dict2.setDictName("test2");

		Dict dict3 = new Dict();
		dict3.setDictId(3);
		dict3.setDictName("test3");

		Map<Integer, Dict> mapStatus = new HashMap<>();
		mapStatus.put(1, dict);
		mapStatus.put(2, dict2);
		mapStatus.put(3, dict3);
		return mapStatus;
	}
}
