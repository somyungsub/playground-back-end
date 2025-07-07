package prototype.hexa.common.port.out;

public interface EventProducer<T> {
  void send(String topicName, T t);
}
