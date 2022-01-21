import org.junit.Test
import org.junit.Assert.*

class Test1:
  @Test def t1(): Unit = 
    assertEquals("I was compiled by Scala 3. :)", msg)

  
  class UserInterestPolicy(userService: UserService, brandService: BrandService) 
    extends Policy[UserInterest]{
      def check(userInterest: UserInterest)(implicit args: PolicyArguments, scope: ScopedController) : Boolean = {
        def checkExpanded(brand: Brand, userId: UserId) : Boolean = { 
          val badBrands : List[Brand] = userService.fetchBlackListedBrands(userId)
          if(badBrands.contains(brand)){
            throw InvalidPolicyAccessException(s"Brand ${brand.name} is not allowed to access data for user $userId")
          }else{
            userInterest
          }
        }
        PolicyArguments(checkExpanded _, args)
    }
  }

  implicit BrandScope : ScopeController

  def funcIwantToDebug(){
    implicit DevScope 
  }

  class ScopeController 

  def getUserInterestForRelevantUsers(@policyParam brand: Brand) : Tuple2[UserId, List[UserInterest]]{
    userService.fetchUsersByInterests(brand.category).map(_ => {
      @policyParam val userId: UserId = _.id
      (userId, _.interests)
    }
  }

  val userAge : UserAgePolicy[Int] = UserAgePolicy(2)
  val x = 5 + userAge
  val x2 = userAge + 10 


  val userPolicy = UserPolicy(User("Sinan", List(....)))

  val uiPolicy : UserInterestPolicy = userPolicy.interests 