package javassisttest;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.ExecutionException;

/**
 * ��������copy ���пɸ�������ȫ��copy
 * or merge ֻҪ��Ϊnull�ĲŸ���
 */
public class Copier {


    // copy��Ļ��棬����1024
    private static Cache<Key, Copy> copierCache = CacheBuilder.newBuilder().maximumSize(1024).build();

    /**
     * �������Կ���
     *
     * @param from             Դ����
     * @param to               Ŀ�����
     * @param ignoreProperties ��������ֵ
     * @param <F>              Դ��������
     * @param <T>              Ŀ���������
     * @return Ŀ�����ͬ�ڶ�������to
     */
    public static <F, T> T copy(final F from, final T to, final String ignoreProperties) {
        Copy copier = getCopy(from, to);
        copier.copy(from, to, ignoreProperties);
        return to;
    }

    public static <F, T> T copy(final F from, final T to) {

        return copy(from, to, null);
    }

    /**
     * �������Կ���
     *
     * @param from             Դ����
     * @param to               Ŀ�����
     * @param ignoreProperties ��������ֵ
     * @param <F>              Դ��������
     * @param <T>              Ŀ���������
     * @return Ŀ�����ͬ�ڶ�������to
     */
    public static <F, T> T merge(final F from, final T to, final String ignoreProperties) {

        Copy copier = getCopy(from, to);
        copier.merge(from, to, ignoreProperties);
        return to;

    }

    public static <F, T> T merge(final F from, final T to) {

        return merge(from, to, null);

    }

    private static <F, T> Copy getCopy(F from, T to) {
        try {
            Key key = getKey(from, to);
            Copy copier = copierCache.get(key, () -> {
                Generator gen = new Generator();
                gen.setSource(from.getClass());
                gen.setTarget(to.getClass());
                return gen.generate().newInstance();
            });
            return copier;
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private static Key getKey(Object from, Object to) {
        Class<?> fromClass = from.getClass();
        Class<?> toClass = to.getClass();
        return new Key(fromClass, toClass);
    }


}

