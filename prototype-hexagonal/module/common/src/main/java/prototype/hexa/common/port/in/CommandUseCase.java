package prototype.hexa.common.port.in;

public interface CommandUseCase<Domain, Command, ID> {
  Domain save(Command domain);
  Domain update(ID id);
  void delete(ID id);

}
