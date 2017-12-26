import java.io.*;
import java.util.*;
import java.util.stream.*;

class Solution {

  public static List<String> getLongestSequence(List<String> userA, List<String> userB) {
    // IMPLEMENTATION GOES HERE

    //firstly if there are any common consequetive elements, then the subset will be matching
    List<String> longest= new ArrayList<String>();
    int common= isCommon(userA, userB); 
    
    if(common==0)
    {
      
      return  longest; //empty list
    }
    
    //find if the common words found are consecutive, special case is when there is only one common word
    if(common==1)
    {
      
      return commonWord(userA, userB);
    }
    
    //other cases
    
    //find common word, then find their indexes, and store the indexes in a list, check if any of their subset is increasing in order
    List<String> commonWords = commonWord(userA,userB);
    System.out.println("common words are: "+commonWords);
    int[] indexA= wordIndexes(userA, commonWords);
    System.out.println("corresponding indexes in a and b: ");
    int[] indexB= wordIndexes(userB, commonWords);
    for(int m=0;m<indexA.length;m++)
    {
      System.out.println("a: "+ indexA[m]+" b: "+indexB[m]);
    }
    
    //comparing the arrays
    
    //2,4,5,6; 0,7,8,9; -,3,3,3 differences would same
    
    int[] diff= new int[indexA.length];
    for(int i=0; i<indexA.length;i++)
    {
      diff[i]=indexA[i]-indexB[i];
      
    }
   
    // array of diff: 2,3,3,3,5,6,6,6,6
    //find max count and value
    
    int currmax=0; 
    int max=0; //number of elements
    int maxValIndex=0; //value of equals
    int currValInd=0;
    Set<Integer> equals=new HashSet<>();
    for(int j=0; j<diff.length-1;j++)
    {
      if(diff[j]==diff[j+1])
      {
         equals.add(j);
         equals.add(j+1);
         currmax= equals.size();
        currValInd= j+1;
      }
      else{
        max= max>=currmax?max:currmax;
        maxValIndex= max>=currmax?maxValIndex:currValInd;
        equals.clear();
      }
      
    }
    
    
    System.out.println("equals: "+equals);
    
    for(int v: equals)
    {
      longest.add(commonWords.get(v));
    }
    
    
    
    
    return longest;
  }
  
  public static int[] wordIndexes(List<String> main, List<String> subset)
  {
      int len= subset.size();
      int[] indexes= new int[len];
      for(int i=0; i<subset.size();i++)
      {
        indexes[i]= main.indexOf(subset.get(i));
      }
    
       return indexes;
  }
  
  public static List<String> commonWord(List<String> a, List<String> b)
  {
     List<String> common=new ArrayList<String>();
    for(int i=0; i<a.size(); i++)
    {
        if( b.contains(a.get(i)))
           {
             common.add(a.get(i));
           }
    }
    return common;
    
  }
  
  public static int isCommon(List<String> a, List<String> b)
  {
    int common=0;
    for(int i=0; i<a.size(); i++)
    {
        if( b.contains(a.get(i)))
           {
             common++;
           }
    }
    return common;
  }


  // START TEST CASES
  //
  // You can add test cases below. Each test case should be an instance of Test
  // constructed with:
  //
  // Test(String name, List<String> userA, List<String> userB, List<String> expectedOutput);
  //

  private static final List<Test> tests = Arrays.asList(
    new Test(
      // name
      "sample input #1",
      // userA
      Arrays.asList("/nine.html", "/four.html", "/six.html", "/seven.html", "/one.html"),
      // userB
      Arrays.asList("/nine.html", "/two.html", "/three.html", "/four.html", "/six.html", "/seven.html"),
      // expectedOutput
      Arrays.asList("/four.html", "/six.html", "/seven.html")
    ), 
    new Test(
      // name
      "sample input #2",
      // userA
      Arrays.asList("/one.html", "/two.html", "/three.html", "/four.html", "/six.html"),
      // userB
      Arrays.asList("/nine.html", "/two.html", "/three.html", "/four.html", "/six.html", "/seven.html"),
      // expectedOutput
      Arrays.asList("/two.html", "/three.html", "/four.html", "/six.html")
    ), 
    new Test(
      // name
      "sample input #3",
      // userA
      Arrays.asList("/nine.html", "/four.html", "/six.html", "/seven.html", "/one.html"),
      // userB
      Arrays.asList("/three.html", "/eight.html"),
      // expectedOutput
      Arrays.asList()
    ), 
    new Test(
      // name
      "sample input #4",
      // userA
      Arrays.asList("/one.html", "/two.html", "/three.html", "/four.html", "/six.html"),
      // userB
      Arrays.asList("/three.html", "/eight.html"),
      // expectedOutput
      Arrays.asList("/three.html")
    )
  );


  // END TEST CASES
  // DO NOT MODIFY BELOW THIS LINE

  private static class Test {

    public String name;
    public List<String> userA;
    public List<String> userB;
    public List<String> expectedOutput;

    public Test(String name, List<String> userA, List<String> userB, List<String> expectedOutput) {
      this.name = name;
      this.userA = userA;
      this.userB = userB;
      this.expectedOutput = expectedOutput;
    }
  }

  private static boolean equalOutputs(List<String> a, List<String> b) {
    return a.equals(b);
  }

  public static void main(String[] args) {
    int passed = 0;
    for (Test test : tests) {
      try {
        System.out.printf("==> Testing %s...\n", test.name);
        List<String> actualOutput = getLongestSequence(test.userA, test.userB);
        if (equalOutputs(actualOutput, test.expectedOutput)) {
          System.out.println("PASS");
          passed++;
        } else {
          System.out.println("FAIL");
          System.out.printf("Expected output: %s\n", test.expectedOutput);
          System.out.printf("Actual output: %s\n", actualOutput);
        }
      } catch (Exception e) {
        System.out.println("FAIL");
        System.out.println(e);
      }
    }
    System.out.printf("==> Passed %d of %d tests\n", passed, tests.size());
  }
}