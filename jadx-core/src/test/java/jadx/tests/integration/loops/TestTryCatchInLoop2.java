package jadx.tests.integration.loops;

import jadx.core.dex.nodes.ClassNode;
import jadx.tests.api.IntegrationTest;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import static jadx.tests.api.utils.JadxMatchers.containsOne;
import static org.junit.Assert.assertThat;

public class TestTryCatchInLoop2 extends IntegrationTest {

	public static class TestCls<T extends String> {
		private static class MyItem {
			int idx;
			String name;
		}

		private final Map<Integer, MyItem> mCache = new HashMap<Integer, MyItem>();

		void test(MyItem[] items) {
			synchronized (this.mCache) {
				for (int i = 0; i < items.length; ++i) {
					MyItem existingItem = mCache.get(items[i].idx);
					if (null == existingItem) {
						mCache.put(items[i].idx, items[i]);
					} else {
						existingItem.name = items[i].name;
					}
				}
			}
		}
	}

	@Test
	public void test() {
		ClassNode cls = getClassNode(TestCls.class);
		String code = cls.getCode().toString();

		assertThat(code, containsOne("synchronized (this.mCache) {"));
		assertThat(code, containsOne("for (int i = 0; i < items.length; i++) {"));
	}
}
