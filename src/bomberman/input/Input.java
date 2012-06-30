package bomberman.input;

public interface Input {

public boolean use(int key);
public boolean get(int key);
public boolean netget(int type);
public void clear();
}