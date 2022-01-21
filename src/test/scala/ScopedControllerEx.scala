import java.sql.Date
class ScopedControllerEx:

  class ReligionPolicy extends Policy[UserInterest]{
      def check(userInterest: UserInterest)(implicit args: PolicyArguments, scope: ScopedController) : Boolean = {
        if(userInterest.category == "Religion"){
          throw InvalidPolicyAccessException(s"Religious Affiliation Can Not Be Shared With Advertisers")
        }else{
          userInterest
        }
    }
  }

  case class UserInterest(
    category: String,
    value: String
  )

  chaperone class User(
    id: UUID,
    name: String,
    lastname: String, 
    birthdate: Date,
    city: String,
    country: String,
    religion: UserInterest with ReligionPolicy,
  ){

    def validateSSN() = {
      log.info(user.ssn)
    }
  }

  def getUserReligionDemographics() : Map[String, Int] = {
    userService.getUsers()(0).validateSSN(); //.groupBy(_.religion).map((k, v) => (k, v.length))
  }

  case object InternalPipelineScope extends ScopedController

  class ReligionPolicy extends Policy[UserInterest]{
      def check(userInterest: UserInterest)(implicit args: PolicyArguments, scope: ScopedController) : Boolean = {
        scope match{
          case InternalPipelineScope => userInterest
          case _ => 
            if(userInterest.category == "Religion"){
              throw InvalidPolicyAccessException(s"Religious Affiliation Can Not Be Shared With Advertisers")
            }else{
              userInterest
            }
        }
    }
  }

  if(check){
    policydata.method2
  }

  def method2(a: Policy[A]){
    if(check){
      body here
      ..
      println(a)
    }
  }


