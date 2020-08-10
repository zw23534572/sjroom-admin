package github.sjroom.admin.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: TreeUtils
 * @description: 数形工具类
 * @author: hedong@ibeesaas.com
 * @date: 2019-01-04 16:28
 **/
public class TreeUtils {


	/**
	 * 解析树形数据
	 *
	 * @param topId
	 * @param entityList
	 * @return
	 */
	public static <E extends TreeEntity<E>> List<E> getTreeList(Long topId, List<E> entityList) {
		List<E> resultList = new ArrayList<>();

		//获取顶层元素集合
		Long parentId;
		for (E entity : entityList) {
			parentId = entity.getParentId();
			if (parentId == null || topId.equals(parentId)) {
				resultList.add(entity);
			}
		}

		//获取每个顶层元素的子数据集合
		for (E entity : resultList) {
			entity.setChildren(getSubList(entity.getId(), entityList));
		}

		return resultList;
	}

	/**
	 * 获取子数据集合
	 *
	 * @param id
	 * @param entityList
	 * @return
	 */
	private static <E extends TreeEntity<E>> List<E> getSubList(Long id, List<E> entityList) {
		List<E> childList = new ArrayList<>();
		Long parentId;

		//子集的直接子对象
		for (E entity : entityList) {
			parentId = entity.getParentId();
			if (id.equals(parentId)) {
				childList.add(entity);
			}
		}

		//子集的间接子对象
		for (E entity : childList) {
			entity.setChildren(getSubList(entity.getId(), entityList));
		}

		//递归退出条件
		if (childList.size() == 0) {
			return childList;
		}

		return childList;
	}


}
