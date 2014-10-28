package cn.superid.id_generator.exceptions;

/**
 * 元信息中表没有group异常
 * Created by 维 on 2014/9/3.
 */
public class NoGroupOfTableException extends IdGeneratorException {
    public NoGroupOfTableException(String message) {
        super(message);
    }
}
