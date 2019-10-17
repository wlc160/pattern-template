package com.mytask.template.product;

/**
 * 工程名:pattern-template
 * 包名:mytask.template.product
 * 文件名:DevelopProduct
 * description:
 *
 * @author lcwen
 * @version V1.0: DevelopProduct.java 2019/10/17 8:37
 **/
public abstract class DevelopProduct {


    /**
     *
     */
    protected final void createProduct(){

        //项目启动
        this.startup();

        //需求调研
        this.survey();

        //指定研发计划
        this.plan();

        //系统设计、详细设计
        this.design();

        //设计评审
        this.review();

        //开发
        this.develop();

        //测试
        this.test();

        //培训，有些不需要培训
        if (this.isTrain()){
            this.train();
        }

        //上线
        this.online();
    }

    abstract void train();

    protected boolean isTrain(){
       return false;
    }


    final void startup(){
        System.out.println("项目启动、立项");
    }

    final void survey() {
        System.out.println("需求调研");
    }

    final void plan(){
        System.out.println("设定研发计划");
    }

    final void design(){
        System.out.println("系统设计/详细设计");
    }

    final void review(){
        System.out.println("设计评审");
    }

    final void develop(){
        System.out.println("产品开发");
    }

    final void test(){
        System.out.println("产品测试");
    }

    final void online(){
        System.out.println("产品上线");
    }

}
