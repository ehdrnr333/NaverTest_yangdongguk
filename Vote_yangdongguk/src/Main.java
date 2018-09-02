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