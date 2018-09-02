import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * project name : Vote_yangdongguk
 * author : Dongguk Yang
 * 
 * class Main
 * �ش� ������ �ΰ��� ������� Ǯ�����ϴ�.
 * #1. HashMap�� �̿��� ����Դϴ�.
 * #2. TrieMap�� �̿��� ����Դϴ�.(���� �ۼ��� Ŭ����)
 * 
 * - TrieMap votemapFromTxt(String fileName) : �ؽ�Ʈ ������ �о TrieMap�� �����Ͽ� ��ȯ�մϴ�.
 * - LinkedList<String> solveFromHashMap(String fileName) : ������ �а� HashMap�� �����Ͽ� ���� ���� ��ǥ�� �̸��� ����Ʈ�� ��ȯ�մϴ�.
 */

public class Main {
	public static void main(String[] args) {
		// �׽�Ʈ ���̽� 1���� 11���� �����մϴ�.
		for (int i = 1; i <= 11; ++i) {
			// #1. HashMap
			// �׽�Ʈ ���̽��� �о ���� �� result�� �����մϴ�.
			long start1 = System.currentTimeMillis();
			LinkedList<String> result1 =  solveFromHashMap("Vote_testcase/" + i + ".input.txt");
			long end1 = System.currentTimeMillis();
			double response_time1 = (end1 - start1) / 1000.0;

			// #1. ����ð��� ������ ����մϴ�.
			System.out.println(i + ". Hash Response Time : " + response_time1);
			for (int j = 0; j < result1.size(); ++j)
				System.out.print(result1.get(j) + " ");
			System.out.println();
			
			// #2. TrieMap
			// �׽�Ʈ ���̽��� �о ���� �� result�� �����մϴ�.
			long start2 = System.currentTimeMillis();
			TrieMap votemap = votemapFromTxt("Vote_testcase/" + i + ".input.txt");
			LinkedList<String> result2 = votemap.voteResult();
			long end2 = System.currentTimeMillis();
			double response_time2 = (end2 - start2) / 1000.0;

			// #2. ����ð��� ������ ����մϴ�.
			System.out.println(i + ". Trie Response Time : " + response_time2);
			for (int j = 0; j < result2.size(); ++j)
				System.out.print(result2.get(j) + " ");
			System.out.println();
			
			// ���� �ؽ�Ʈ�� ���� �� ����մϴ�.
			LinkedList<String> outputList = new LinkedList<String>();
			try {
				BufferedReader br = new BufferedReader(new FileReader("Vote_testcase/" + i + ".output.txt"));
				String line;
				while ((line = br.readLine()) != null) {
					outputList.add(line);
				}
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (int j = 0; j < outputList.size(); ++j) {
				System.out.print(outputList.get(j) + " ");
			}
			System.out.println();
		}
	}

	
    /**
     * #2. TrieMap�� �̿��� ���
     * �ؽ�Ʈ���Ͽ��� ���ڿ��� ���پ� �о TrieMap�� �����ϰ� ��ȯ�մϴ�.
     * @param fileName ���� �ؽ�Ʈ ���� �̸��Դϴ�.
     * @return �ϼ��� TrieMap�� ��ȯ�մϴ�.
     */
	public static TrieMap votemapFromTxt(String fileName) {
		TrieMap votemap = new TrieMap();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			while (true) {
				String line = br.readLine();
				if (line == null)
					break;
				votemap.put(line);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return votemap;
	}

    /**
     * #1. HashMap�� �̿��� ���
     * �� �ܰ�� ����˴ϴ�. (�ؽ�Ʈ ���� �б�� ����)
     * @param fileName ���� �ؽ�Ʈ ���� �̸��Դϴ�.
     * @return ���� ���� ��ǥ�� �̸��� ����� ����Ʈ�Դϴ�.
     */
	public static LinkedList<String> solveFromHashMap(String fileName) {
		
		// �ؽø��� �����ϰ� �ؽ�Ʈ���Ϸκ��� ���پ� �н��ϴ�.
		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = br.readLine()) != null) {
				/*
				 * �ؽø��� Ű������ ���� ���ڿ��� �����մϴ�.
				 * ���ڿ��� �ؽøʿ� Ű������ ������ ��� ���� 1 ������ŵ�ϴ�.
				 */
				if (hashMap.containsKey(line)) {
					hashMap.put(line, hashMap.get(line) + 1);
				} else {
					hashMap.put(line, 1);
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// �ִ� count�� ���� ���ڿ��� answer����Ʈ�� �����մϴ�.
		int max = 0;
		LinkedList<String> answer = new LinkedList<String>();
		for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
			int value = entry.getValue();
			if (value > max) {
				answer.clear();
				answer.add(entry.getKey());
				max = value;
			} else if (value == max) {
				answer.add(entry.getKey());
			}
		}
		// ������ answer����Ʈ�� ���ĺ� ������ �����մϴ�.
		answer.sort(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		return answer;
	}
}

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

class TrieMap {
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