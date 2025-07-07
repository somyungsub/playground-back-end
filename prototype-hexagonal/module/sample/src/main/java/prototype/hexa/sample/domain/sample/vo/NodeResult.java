package prototype.hexa.sample.domain.sample.vo;


public record NodeResult(ResultData data) {
  public Long getResult1() {
    return data.result1;
  }

  public Long getResult2() {
    return data.result2;
  }

  public Long getResult3() {
    return data.result3;
  }
  public long getSum() {
    return data.result4.sum;
  }
  public long getMinus() {
    return data.result4.minus;
  }
  public long getMulti() {
    return data.result4.multi;
  }

  private record ResultData(long result1, long result2, long result3, CalcResult result4) {
  }

  private record CalcResult(long sum, long minus, long multi) {
  }
}
