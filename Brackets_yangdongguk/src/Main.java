import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * project name : Brackets_yangdongguk
 * author : Dongguk Yang
 * 
 * class Main
 * - int LIMIT_NUM : 연산 과정에서 정수 오버플로가 일어나지 않도록 %연산을 수행하기 위한 상수입니다.
 * 
 * - int tsNum(char brarcket) : 입력된 괄호 문자를 숫자로 변환하여 반환하는 함수입니다.
 * - int bracketsResult(String brackets) : 하나의 괄호 문자열을 입력받아 연산 후 결과값을 반환하는 함수입니다.
 * - LinkedList<String> listFromTxt(String fileName) : 텍스트 파일을 읽어서 LinkedList에 저장하여 반환합니다.
 */

public class Main {
	public static final int LIMIT_NUM = 100000000;
	public static void main(String[] args) {
		// 테스트 케이스 1부터 10까지 진행합니다.
		for (int i = 1; i <= 10; ++i) {
			// 테스트 케이스를 읽어서 연산 후 result에 저장합니다.
			long start = System.currentTimeMillis();
			LinkedList<String> inputList = listFromTxt("Brackets_testcase/" + i + ".input.txt");
			LinkedList<Integer> result = new LinkedList<Integer>();
			for (int j = 1; j < inputList.size(); ++j) {
				result.add(bracketsResult(inputList.get(j)));
			}
			long end = System.currentTimeMillis();
			double response_time = (end - start) / 1000.0;
			
			// 경과시간과 정답을 출력합니다.
			System.out.println(i+". Response Time : " + response_time);
			for (int j = 0; j < result.size(); ++j)
				System.out.print(result.get(j) + " ");
			System.out.println();

			// 정답 텍스트를 읽은 후 출력합니다.
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
     * 하나의 괄호 문자열을 입력받아 연산 후 결과값을 반환하는 함수입니다.
     * 문자열을 읽고 패턴을 분석하여 연산을 단계적으로 진행합니다.
     * 이 때 패턴에 따라 중간 연산값이나 괄호(숫자)를 저장해 두는 변수가 필요합니다.
     * 해당 변수가 stairs와 step입니다.
     * 
     * @param brackets 괄호로 구성된 문자열입니다.
     * @return brackets를 연산한 결과를 정수형으로 반환합니다.
     */
	public static int bracketsResult(String brackets) {
		/*
		 * <변수 설명>
		 * LinkedList<LinkedList<Integer>> stairs : 
		 * 괄호 패턴에 따라 중간 연산값이나 괄호(숫자)를 저장하는 리스트입니다.
		 * 리스트의 1차원에서 괄호의 중첩단계를 나타내고 2차원에서 해당 단계에 속하는 괄호값을 차례로 저장합니다.
		 * int step : 진행된 순서에서 현재 위치한 단계를 나타냅니다.
		 * int answer : 연산 결과값입니다.
		 */
		LinkedList<LinkedList<Integer>> stairs = new LinkedList<LinkedList<Integer>>();
		int step = 0;
		int answer = 0;
		
		/*
		 * <초기화 영역>
		 * brackets의 0번째 문자가 닫힌문자(']')일 경우 연산을 중단합니다.
		 * 열린문자일 경우 계단의 첫단계를 만들고 첫문자를 숫자로 변환하여 저장합니다.
		 */
		if (tsNum(brackets.charAt(0)) > 3)
			return 0;
		stairs.add(new LinkedList<Integer>());
		stairs.get(0).add(tsNum(brackets.charAt(0)));

		/*
		 * <반복>
		 * brackets의 1부터 문자열의 길이만큼 반복합니다.
		 * b1과 b2는 문자열의 연속된 두 문자입니다.
		 * b1과 b2를 합쳐 하나의 패턴을 만들고 해당 패턴을 분석합니다.
		 */
		for (int i = 1; i < brackets.length(); ++i) {
			int b1 = tsNum(brackets.charAt(i - 1));
			int b2 = tsNum(brackets.charAt(i));

			/*
			 * <패턴 "X[">
			 * 변수가 4보다 작을 경우 열린 문자입니다.
			 * 두번째 문자가 열린문자일 경우 새로운 수를 추가 입력합니다.
			 */
			if (b2 < 4) // b2가
			{
				/*
				 * <패턴 "[[">
				 * 해당 패턴에서 단계를 올립니다.
				 */
				if (b1 < 4) {
					stairs.add(new LinkedList<Integer>());
					++step;
				}
				stairs.get(step).add(b2);
			} 
			/*
			 * <패턴 "]]">
			 * 더하기 연산을 진행하고 계단을 내려가 가장 마지막 값과 곱하기 연산을 진행합니다.
			 */
			else if (b1 > 3)
			{
				// 1) 닫힌괄호가 열린괄호를 초과한 경우입니다.
				if (step - 1 < 0)
					return 0;
				// 2) 괄호의 쌍이 맞지않는 경우입니다. 예를들어 "{()]"와 같은 경우입니다.
				int lastVal = stairs.get(step - 1).pollLast();
				if (lastVal != (b2 - 3))
					return 0;

				// 3) 해당 계단의 모든 값을 더합니다. 모두 더하면 해당 계단을 제거합니다.
				int sum = 0;
				for (int k = 0; k < stairs.get(step).size(); ++k) {
					sum = (sum + stairs.get(step).get(k)) % LIMIT_NUM;
				}
				stairs.removeLast();
				
				// 4) 계단을 내려가고 내려간 계단의 마지막 값과 곱합니다.
				--step;
				stairs.get(step).add((lastVal * sum) % LIMIT_NUM);
			} 
			/*
			 * <패턴 "[]" 중에서 서로 다른 괄호일 경우>
			 * 예를들어 "(]", "{)"와 같은 경우 괄호짝이 맞지 않으므로 
			 * 연산을 중단합니다.
			 */
			else if (b1 != (b2 - 3))
			{
				return 0;
			}
		}

		/*
		 * <결과 반환 영역>
		 * step이 0까지 진행되어야 괄호의 짝이 맞는 경우입니다.
		 * step이 0이면 0계단의 모든 값을 정답에 더합니다.
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