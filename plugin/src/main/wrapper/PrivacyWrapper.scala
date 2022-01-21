package wrapper

import java.lang.reflect.{Proxy, InvocationHandler, Method}

sealed trait Policy[S]{
 def check(s: S): Boolean
}

private class PolicyInvocationHandler[S](policy: Policy[S]) extends InvocationHandler{
   def invoke(proxy: AnyRef, method: Method, args: Array[AnyRef]): AnyRef = {
      println("Invoking \"%s\"".format(method.getName))
      if(policy.check(proxy)){
        result  = method.invoke(proxy, args)
        
      }else{
        
      }

   }
}



