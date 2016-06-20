package proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MyCglibProxy implements MethodInterceptor {

    //public Enhancer enhancer = new Enhancer();
    private String name;

    public MyCglibProxy(String name) {
        this.name = name;
    }

    /**
     * ����class���󴴽��ö���Ĵ������ 1�����ø��ࣻ2�����ûص� ���ʣ���̬������һ��class���������
     *
     * @param
     * @return
     */
    /*public Object getDaoBean(Class cls) {
        enhancer.setSuperclass(cls);
        enhancer.setCallback(this);
        return enhancer.create();
    }*/
    public Object intercept(Object object, Method method, Object[] args,
                            MethodProxy methodProxy) throws Throwable {
        System.out.println("���õķ����ǣ�" + method.getName());
        // �û������ж�
        if (!"����".equals(name)) {
            System.out.println("��û��Ȩ�ޣ�");
            return null;
        }
        Object result = methodProxy.invokeSuper(object, args);

        return result;
    }
}