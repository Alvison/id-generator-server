package cn.superid.id_generator.exceptions;

/**
 * 找不到合适的服务器
 * Created by 维 on 2014/8/30.
 */
public class NoAvailableServerException extends IdGeneratorException {
    public NoAvailableServerException(String message) {
        super(message);
    }
}
