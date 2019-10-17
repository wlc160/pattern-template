**设计模式之模板模式**

**1、定义：**
一个算法的骨架，并允许子类为一个或者多个步骤提供实现。模板方法使得子类可以在不改变算法结构的情况下，重新定义算法的某些步骤，属于行为性设计模式

**2、应用场景：**

（1）一次性实现一个算法的不变的部分，并将可变的行为留给子类来实现。

（2）各子类中公共的行为被提取出来并集中到一个公共的父类中，从而避免代码重复。

**3、模板模式在源码中的体现**

先来看 JDK 中的 AbstractList，来看代码：
````
package java.util; 
public abstract class AbstractList<E> extends AbstractCollection<E> implements List<E> { 
    ... 
    abstract public E get(int index); 
    ... 
}
````
我们看到 get()是一个抽象方法，那么它的逻辑就是交给子类来实现，我们大家所熟知的ArrayList就是AbstractList的子类 。 
同理，有AbstractList就有AbstractSet 和 AbstractMap，有兴趣的小伙伴可以去看看这些的源码实现。
还有一个每天都在用的HttpServlet，有三个方法service()和doGet()、doPost()方法，都是模板方法的抽象实现。


在MyBatis框架也有一些经典的应用，我们来一下BaseExecutor类，它是一个基础的SQL执行类，实现了大部分的SQL执行逻辑，然后把几个方法交给子类定制化完成，源码如下：
```
...
public abstract class BaseExecutor implements Executor {
    ...
    protected abstract int doUpdate(MappedStatement var1, Object var2) throws SQLException;
    
    protected abstract List<BatchResult> doFlushStatements(boolean var1) throws SQLException;
    
    protected abstract <E> List<E> doQuery(MappedStatement var1, Object var2, RowBounds var3, ResultHandler var4, BoundSql var5) throws SQLException;
    
    protected abstract <E> Cursor<E> doQueryCursor(MappedStatement var1, Object var2, RowBounds var3, BoundSql var4) throws SQLException;
    
    ...
}
````
如 doUpdate、doFlushStatements、doQuery、doQueryCursor 这几个方法就是交由子类来实现，那么BaseExecutor有哪些子类呢？我们来看一下它的类图：

![Image text](https://github.com/wlc160/img-folder/blob/master/TemplateExecutor.jpg)

我们一起来看一下SimpleExecutor的doUpdate实现：

````
public int doUpdate(MappedStatement ms, Object parameter) throws SQLException {
    Statement stmt = null;
    int var6;
    try {
        Configuration configuration = ms.getConfiguration();
        StatementHandler handler = configuration.newStatementHandler(this, ms, parameter, RowBounds.DEFAULT, (ResultHandler)null, (BoundSql)null);
        stmt = this.prepareStatement(handler, ms.getStatementLog()); 
        var6 = handler.update(stmt);
       } finally { 
            this.closeStatement(stmt); 
       }
    return var6;
}
````
再来对比一下BatchExecutor的doUpate实现：
````
public int doUpdate(MappedStatement ms, Object parameterObject) throws SQLException { 
    Configuration configuration = ms.getConfiguration(); 
    StatementHandler handler = configuration.newStatementHandler(this, ms, parameterObject, RowBounds.DEFAULT, (ResultHandler)null, (BoundSql)null); 
    BoundSql boundSql = handler.getBoundSql(); 
    String sql = boundSql.getSql(); 
    Statement stmt; 
    if(sql.equals(this.currentSql) && ms.equals(this.currentStatement)) { 
        int last = this.statementList.size() - 1; 
        stmt = (Statement)this.statementList.get(last); 
        this.applyTransactionTimeout(stmt); 
        handler.parameterize(stmt); 
        BatchResult batchResult = (BatchResult)this.batchResultList.get(last); 
        batchResult.addParameterObject(parameterObject); 
    } else { 
        Connection connection = this.getConnection(ms.getStatementLog()); 
        stmt = handler.prepare(connection, this.transaction.getTimeout()); 
        handler.parameterize(stmt); 
        this.currentSql = sql; 
        this.currentStatement = ms; 
        this.statementList.add(stmt); 
        this.batchResultList.add(new BatchResult(ms, sql, parameterObject)); 
    }
        handler.batch(stmt); 
        return -2147482646; 
}
````

**4、优点**

（1）利用模板方法将相同处理逻辑的代码放到抽象父类中，可以提高代码的复用性。

（2）将不同的代码不同的子类中，通过对子类的扩展增加新的行为，提高代码的扩展性。

（3）把不变的行为写在父类上，去除子类的重复代码，提供了一个很好的代码复用平台，符合开闭原则。

**5、缺点**

（1）类数目的增加，每一个抽象类都需要一个子类来实现，这样导致类的个数增加。

（2）类数量的增加，间接地增加了系统实现的复杂度。

（3）继承关系自身缺点，如果父类添加新的抽象方法，所有子类都要改一遍。