import java.util.Comparator;
import java.util.LinkedList;

/**
 * project name : Vote_yangdongguk
 * author : Dongguk Yang
 * 
 * class TrieMap
 * �ڹٿ��� �����ϴ� �ؽø��� ������� �ʰ� ������ �ذ��ϱ� ���� ���� Ŭ�����Դϴ�.
 * �⺻ ���̵��� Trie �ڷᱸ���� �����ϰ� ���ڿ��� ���ڷ� �̾����� Ʈ�����·� �����߽��ϴ�.
 * �ٸ����� ������ �ƽ�Ű�ڵ尪�� �迭�� �ε����� ��� �ش� ���ڿ� �����ϴ� �迭 ���ҿ� ������ �� �ֵ��� �߽��ϴ�. 
 * 
 * - int char_val : �ڽ��� ���ڰ��Դϴ�.
 * - int count : ���⼭ ������ ���ڿ��� �����Դϴ�. 
 * - TrieMap parent : ���� ���ڷ� ���� ���� ���� �����Դϴ�.
 * - TrieMap[] child : ���� ���ڷ� ���� ���� ���� �迭�Դϴ�.
 * - int MAXIMUM_CAPACITY : child �迭�� ũ���Դϴ�. 127�� 2������ 1111111�� ��Ÿ���ϴ�.
 * - LinkedList<TrieMap> referenceList : ���ڿ��� ������ ������ �������� �����ϴ� ����Ʈ�Դϴ�.
 * 
 * - void put(String str) : ���ڿ��� TrieMap�� �߰��մϴ�.
 * - String getString() : ���� ��ġ���� parent�� ���� �ö� ���ڿ��� �ϼ��ϰ� ��ȯ�մϴ�.
 * - LinkedList<String> voteResult() : ��ü TreeMap �� ���� ū count���� ���� TreeMap�� String�� ��Ƽ� ��ȯ�մϴ�.
 */

public class TrieMap {
	private int char_val;
	private int count;
	private TrieMap parent;
	private TrieMap[] child;
	private static final int MAXIMUM_CAPACITY = 127;
	private LinkedList<TrieMap> referenceList = new LinkedList<TrieMap>();

	public TrieMap() {
		child = new TrieMap[MAXIMUM_CAPACITY];
	}
	private TrieMap(TrieMap parent, int index) {
		this.parent = parent;
		this.char_val = index;
		child = new TrieMap[MAXIMUM_CAPACITY];
	}

	public void put(String str) {
		// ���ڿ��� ���̸�ŭ TrieMap�� �����ϰ� �̵��ϴ� ������ ��Ĩ�ϴ�.
		TrieMap curMap = this;
		for (int i = str.length() - 1; i > -1; --i) {
			int cIdx = str.charAt(i);
			if (curMap.child[cIdx] == null) {
				curMap.child[cIdx] = new TrieMap(curMap, cIdx);
			}
			curMap = curMap.child[cIdx];
		}
		// ���ڿ��� ������ ������ �߰��մϴ�.
		if (curMap.count == 0)
			referenceList.add(curMap);
		// ������ ������ ������ ������ŵ�ϴ�.
		++curMap.count;
	}

	
	public String getString() {
		/*
		 * �������� curMap�� �̵��ϸ� ���ڿ��� �ϼ��մϴ�. 
		 * parent�� null�� ��� root����̹Ƿ� �����մϴ�. 
		 */
		String str = "";
		TrieMap curMap = this;
		while (curMap.parent != null) {
			str += (char) curMap.char_val;
			curMap = curMap.parent;
		}
		return str;
	}

	public LinkedList<String> voteResult() {
		// referenceList�� count���� ū ������ �����մϴ�.
		referenceList.sort(new Comparator<TrieMap>() {
			@Override
			public int compare(TrieMap o1, TrieMap o2) {
				if (o1.count < o2.count)
					return 1;
				else if (o1.count > o2.count)
					return -1;
				else
					return 0;
			}
		});

		// �ִ� count�� ���� ���ڿ��� answer����Ʈ�� �����մϴ�.
		LinkedList<String> answer = new LinkedList<String>();
		if (referenceList.size() > 0) {
			int max  = referenceList.get(0).count;
			answer.add(referenceList.get(0).getString());
			for (int i = 1; i < referenceList.size(); ++i) {
				if (referenceList.get(i).count < max)
					break;
				answer.add(referenceList.get(i).getString());
			}

			// ������ answer����Ʈ�� ���ĺ� ������ �����մϴ�.
			answer.sort(new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					return o1.compareTo(o2);
				}
			});
		}
		return answer;
	}
}
