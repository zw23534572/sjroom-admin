package github.sjroom.admin.service;

import github.sjroom.admin.bean.entity.Dict;

import java.util.Map;
import java.util.Set;

public interface IDictService {
	void echo();

	Map<Integer, Dict> findMap(Set<Long> ids) throws Exception;
}

