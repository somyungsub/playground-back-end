public class JavaTest3 {
  public static void main(String[] args) {
    Data data = new Data(args[0], 10);
    System.out.println(data);
  }

  static class Data{
    String name;
    int age;
    public Data(String name, int age){
      this.name = name;
      this.age = age;
    }
  }
}
