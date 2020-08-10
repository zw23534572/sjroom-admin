package github.sjroom.core.mybatis.mapper.test;

import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.support.ColumnCache;
import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;
import org.apache.ibatis.reflection.property.PropertyNamer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

public class MybatisPlusTest {

	@Before
	public void setup() {
		LambdaUtils.installCache(TableInfoHelper.initTableInfo(null, User.class));
	}

	@Test
	public void test1() {
		SerializedLambda lambda = LambdaUtils.resolve(User::getName);
		String fieldName = PropertyNamer.methodToProperty(lambda.getImplMethodName());
		Map<String, ColumnCache> columnMap = LambdaUtils.getColumnMap(lambda.getInstantiatedMethodType());
		ColumnCache columnOfProperty = columnMap.get(LambdaUtils.formatKey(fieldName));
		String column = columnOfProperty.getColumn();
		Assert.assertEquals("name", column);
	}

}
