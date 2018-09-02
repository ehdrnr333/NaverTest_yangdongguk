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
 * 해당 문제를 두가지 방법으로 풀었습니다.
 * #1. HashMap을 이용한 방법입니다.
 * #2. TrieMap을 이용한 방법입니다.(직접 작성한 클래스)
 * 
 * - TrieMap votemapFromTxt(String fileName) : 텍스트 파일을 읽어서 TrieMap에 저장하여 반환합니다.
 * - LinkedList<String> solveFromHashMap(String fileName) : 파일을 읽고 HashMap에 저장하여 가장 많이 투표한 이름을 리스트로 반환합니다.
 */

public class Main {
	public static void main(String[] args) {
		// 테스트 케이스 1부터 11까지 진행합니다.
		for (int i = 1; i <= 11; ++i) {
			// #1. HashMap
			// 테스트 케이스를 읽어서 연산 후 result에 저장합니다.
			long start1 = System.currentTimeMillis();
			LinkedList<String> result1 =  solveFromHashMap("Vote_testcase/" + i + ".input.txt");
			long end1 = System.currentTimeMillis();
			double response_time1 = (end1 - start1) / 1000.0;

			// #1. 경과시간과 정답을 출력합니다.
			System.out.println(i + ". Hash Response Time : " + response_time1);
			for (int j = 0; j < result1.size(); ++j)
				System.out.print(result1.get(j) + " ");
			System.out.println();
			
			// #2. TrieMap
			// 테스트 케이스를 읽어서 연산 후 result에 저장합니다.
			long start2 = System.currentTimeMillis();
			TrieMap votemap = votemapFromTxt("Vote_testcase/" + i + ".input.txt");
			LinkedList<String> result2 = votemap.voteResult();
			long end2 = System.currentTimeMillis();
			double response_time2 = (end2 - start2) / 1000.0;

			// #2. 경과시간과 정답을 출력합니다.
			System.out.println(i + ". Trie Response Time : " + response_time2);
			for (int j = 0; j < result2.size(); ++j)
				System.out.print(result2.get(j) + " ");
			System.out.println();
			
			// 정답 텍스트를 읽은 후 출력합니다.
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
     * #2. TrieMap을 이용한 방법
     * 텍스트파일에서 문자열을 한줄씩 읽어서 TrieMap에 저장하고 반환합니다.
     * @param fileName 읽을 텍스트 파일 이름입니다.
     * @return 완성된 TrieMap을 반환합니다.
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
     * #1. HashMap을 이용한 방법
     * 두 단계로 진행됩니다. (텍스트 파일 읽기와 정렬)
     * @param fileName 읽을 텍스트 파일 이름입니다.
     * @return 가장 많이 투표한 이름이 저장된 리스트입니다.
     */
	public static LinkedList<String> solveFromHashMap(String fileName) {
		
		// 해시맵을 생성하고 텍스트파일로부터 한줄씩 읽습니다.
		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = br.readLine()) != null) {
				/*
				 * 해시맵의 키값으로 읽은 문자열을 전달합니다.
				 * 문자열이 해시맵에 키값으로 존재할 경우 값을 1 증가시킵니다.
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
		
		// 최대 count를 가진 문자열을 answer리스트에 저장합니다.
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
		// 저장한 answer리스트를 알파벳 순으로 정렬합니다.
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
 * 자바에서 제공하는 해시맵을 사용하지 않고 문제를 해결하기 위해 만든 클래스입니다.
 * 기본 아이디어는 Trie 자료구조와 유사하게 문자에서 문자로 이어지는 트리형태로 구성했습니다.
 * 다른점은 문자의 아스키코드값을 배열의 인덱스로 삼아 해당 문자에 대응하는 배열 원소에 접근할 수 있도록 했습니다. 
 * 
 * - int char_val : 자신의 문자값입니다.
 * - int count : 여기서 끝나는 문자열의 개수입니다. 
 * - TrieMap parent : 이전 문자로 가기 위한 참조 변수입니다.
 * - TrieMap[] child : 다음 문자로 가기 위한 참조 배열입니다.
 * - int MAXIMUM_CAPACITY : child 배열의 크기입니다. 127은 2진수로 1111111을 나타냅니다.
 * - LinkedList<TrieMap> referenceList : 문자열이 끝나는 지점의 참조값만 저장하는 리스트입니다.
 * 
 * - void put(String str) : 문자열을 TrieMap에 추가합니다.
 * - String getString() : 현재 위치에서 parent를 통해 올라가 문자열을 완성하고 반환합니다.
 * - LinkedList<String> voteResult() : 전체 TreeMap 중 가장 큰 count값을 가진 TreeMap의 String을 모아서 반환합니다.
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
		// 문자열의 길이만큼 TrieMap을 생성하고 이동하는 과정을 거칩니다.
		TrieMap curMap = this;
		for (int i = str.length() - 1; i > -1; --i) {
			int cIdx = str.charAt(i);
			if (curMap.child[cIdx] == null) {
				curMap.child[cIdx] = new TrieMap(curMap, cIdx);
			}
			curMap = curMap.child[cIdx];
		}
		// 문자열이 끝나는 지점을 추가합니다.
		if (curMap.count == 0)
			referenceList.add(curMap);
		// 끝나는 지점의 개수를 증가시킵니다.
		++curMap.count;
	}

	
	public String getString() {
		/*
		 * 참조변수 curMap을 이동하며 문자열을 완성합니다. 
		 * parent가 null일 경우 root노드이므로 종료합니다. 
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
		// referenceList를 count값이 큰 순서로 정렬합니다.
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

		// 최대 count를 가진 문자열을 answer리스트에 저장합니다.
		LinkedList<String> answer = new LinkedList<String>();
		if (referenceList.size() > 0) {
			int max  = referenceList.get(0).count;
			answer.add(referenceList.get(0).getString());
			for (int i = 1; i < referenceList.size(); ++i) {
				if (referenceList.get(i).count < max)
					break;
				answer.add(referenceList.get(i).getString());
			}

			// 저장한 answer리스트를 알파벳 순으로 정렬합니다.
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