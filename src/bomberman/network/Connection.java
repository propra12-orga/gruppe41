package bomberman.network;

public interface Connection
{
	public void sayHello();

	public void sayReady();

	public void sayStart();

	public void startGame();

	public void update();

	public void transferMap();

	public void disconnect();

	public void close();
}
