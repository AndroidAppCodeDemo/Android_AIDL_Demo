package com.ryg.sayhi.aidl;  
  
import com.ryg.sayhi.aidl.Student;  

// aidl中支持的参数类型为：
// 基本类型（int,long,char,boolean等）,String,CharSequence,List,Map，
// 其他类型必须使用import导入，即使它们可能在同一个包里，比如上面的Student，尽管它和IMyService在同一个包中，但是还是需要显示的import进来。
// 另外，接口中的参数除了aidl支持的类型，其他类型必须标识其方向：
// 到底是输入还是输出抑或两者兼之，用in，out或者inout来表示，上面的代码我们用in标记，因为它是输入型参数。

interface IMyService {  
  
    List<Student> getStudent();

    void addStudent(in Student student);  
} 