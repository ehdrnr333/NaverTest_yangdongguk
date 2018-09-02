import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * project name : Brackets_yangdongguk
 * author : Dongguk Yang
 * 
 * class Main
 * - int LIMIT_NUM : ���� �������� ���� �����÷ΰ� �Ͼ�� �ʵ��� %������ �����ϱ� ���� ����Դϴ�.
 * 
 * - int tsNum(char brarcket) : �Էµ� ��ȣ ���ڸ� ���ڷ� ��ȯ�Ͽ� ��ȯ�ϴ� �Լ��Դϴ�.
 * - int bracketsResult(String brackets) : �ϳ��� ��ȣ ���ڿ��� �Է¹޾� ���� �� ������� ��ȯ�ϴ� �Լ��Դϴ�.
 * - LinkedList<String> listFromTxt(String fileName) : �ؽ�Ʈ ������ �о LinkedList�� �����Ͽ� ��ȯ�մϴ�.
 */

public class Main {
	public static final int LIMIT_NUM = 100000000;
	public static void main(String[] args) {
		// �׽�Ʈ ���̽� 1���� 10���� �����մϴ�.
		for (int i = 1; i <= 10; ++i) {
			// �׽�Ʈ ���̽��� �о ���� �� result�� �����մϴ�.
			long start = System.currentTimeMillis();
			LinkedList<String> inputList = listFromTxt("Brackets_testcase/" + i + ".input.txt");
			LinkedList<Integer> result = new LinkedList<Integer>();
			for (int j = 1; j < inputList.size(); ++j) {
				result.add(bracketsResult(inputList.get(j)));
			}
			long end = System.currentTimeMillis();
			double response_time = (end - start) / 1000.0;
			
			// ����ð��� ������ ����մϴ�.
			System.out.println(i+". Response Time : " + response_time);
			for (int j = 0; j < result.size(); ++j)
				System.out.print(result.get(j) + " ");
			System.out.println();

			// ���� �ؽ�Ʈ�� ���� �� ����մϴ�.
			LinkedList<String> outputList = listFromTxt("Brackets_testcase/" + i + ".output.txt");
			for (int j = 0; j < outputList.size(); ++j) {
				System.out.print(outputList.get(j) + " ");
			}System.out.println();
		}
	}

	public static int tsNum(char brarcket) {
		if (brarcket == '(')
			return 1;
		else if (brarcket == '{')
			return 2;
		else if (brarcket == '[')
			return 3;
		else if (brarcket == ')')
			return 4;
		else if (brarcket == '}')
			return 5;
		else if (brarcket == ']')
			return 6;
		return 0;
	}

    /**
     * �ϳ��� ��ȣ ���ڿ��� �Է¹޾� ���� �� ������� ��ȯ�ϴ� �Լ��Դϴ�.
     * ���ڿ��� �а� ������ �м��Ͽ� ������ �ܰ������� �����մϴ�.
     * �� �� ���Ͽ� ���� �߰� ���갪�̳� ��ȣ(����)�� ������ �δ� ������ �ʿ��մϴ�.
     * �ش� ������ stairs�� step�Դϴ�.
     * 
     * @param brackets ��ȣ�� ������ ���ڿ��Դϴ�.
     * @return brackets�� ������ ����� ���������� ��ȯ�մϴ�.
     */
	public static int bracketsResult(String brackets) {
		/*
		 * <���� ����>
		 * LinkedList<LinkedList<Integer>> stairs : 
		 * ��ȣ ���Ͽ� ���� �߰� ���갪�̳� ��ȣ(����)�� �����ϴ� ����Ʈ�Դϴ�.
		 * ����Ʈ�� 1�������� ��ȣ�� ��ø�ܰ踦 ��Ÿ���� 2�������� �ش� �ܰ迡 ���ϴ� ��ȣ���� ���ʷ� �����մϴ�.
		 * int step : ����� �������� ���� ��ġ�� �ܰ踦 ��Ÿ���ϴ�.
		 * int answer : ���� ������Դϴ�.
		 */
		LinkedList<LinkedList<Integer>> stairs = new LinkedList<LinkedList<Integer>>();
		int step = 0;
		int answer = 0;
		
		/*
		 * <�ʱ�ȭ ����>
		 * brackets�� 0��° ���ڰ� ��������(']')�� ��� ������ �ߴ��մϴ�.
		 * ���������� ��� ����� ù�ܰ踦 ����� ù���ڸ� ���ڷ� ��ȯ�Ͽ� �����մϴ�.
		 */
		if (tsNum(brackets.charAt(0)) > 3)
			return 0;
		stairs.add(new LinkedList<Integer>());
		stairs.get(0).add(tsNum(brackets.charAt(0)));

		/*
		 * <�ݺ�>
		 * brackets�� 1���� ���ڿ��� ���̸�ŭ �ݺ��մϴ�.
		 * b1�� b2�� ���ڿ��� ���ӵ� �� �����Դϴ�.
		 * b1�� b2�� ���� �ϳ��� ������ ����� �ش� ������ �м��մϴ�.
		 */
		for (int i = 1; i < brackets.length(); ++i) {
			int b1 = tsNum(brackets.charAt(i - 1));
			int b2 = tsNum(brackets.charAt(i));

			/*
			 * <���� "X[">
			 * ������ 4���� ���� ��� ���� �����Դϴ�.
			 * �ι�° ���ڰ� ���������� ��� ���ο� ���� �߰� �Է��մϴ�.
			 */
			if (b2 < 4) // b2��
			{
				/*
				 * <���� "[[">
				 * �ش� ���Ͽ��� �ܰ踦 �ø��ϴ�.
				 */
				if (b1 < 4) {
					stairs.add(new LinkedList<Integer>());
					++step;
				}
				stairs.get(step).add(b2);
			} 
			/*
			 * <���� "]]">
			 * ���ϱ� ������ �����ϰ� ����� ������ ���� ������ ���� ���ϱ� ������ �����մϴ�.
			 */
			else if (b1 > 3)
			{
				// 1) ������ȣ�� ������ȣ�� �ʰ��� ����Դϴ�.
				if (step - 1 < 0)
					return 0;
				// 2) ��ȣ�� ���� �����ʴ� ����Դϴ�. ������� "{()]"�� ���� ����Դϴ�.
				int lastVal = stairs.get(step - 1).pollLast();
				if (lastVal != (b2 - 3))
					return 0;

				// 3) �ش� ����� ��� ���� ���մϴ�. ��� ���ϸ� �ش� ����� �����մϴ�.
				int sum = 0;
				for (int k = 0; k < stairs.get(step).size(); ++k) {
					sum = (sum + stairs.get(step).get(k)) % LIMIT_NUM;
				}
				stairs.removeLast();
				
				// 4) ����� �������� ������ ����� ������ ���� ���մϴ�.
				--step;
				stairs.get(step).add((lastVal * sum) % LIMIT_NUM);
			} 
			/*
			 * <���� "[]" �߿��� ���� �ٸ� ��ȣ�� ���>
			 * ������� "(]", "{)"�� ���� ��� ��ȣ¦�� ���� �����Ƿ� 
			 * ������ �ߴ��մϴ�.
			 */
			else if (b1 != (b2 - 3))
			{
				return 0;
			}
		}

		/*
		 * <��� ��ȯ ����>
		 * step�� 0���� ����Ǿ�� ��ȣ�� ¦�� �´� ����Դϴ�.
		 * step�� 0�̸� 0����� ��� ���� ���信 ���մϴ�.
		 */
		if (step == 0) {
			for (int k = 0; k < stairs.get(0).size(); ++k) {
				answer += stairs.get(0).get(k);
			}
		}
		return answer % LIMIT_NUM;
	}

	public static LinkedList<String> listFromTxt(String fileName) {
		LinkedList<String> list = new LinkedList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line=br.readLine())!=null) {
				list.add(line);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}