import java.sql.Date
class TwitterInterests:

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

  class ReligionPolicy extends Policy[UserInterest]{
      def check(userInterest: UserInterest)(implicit args: PolicyArguments, scope: ScopedController) : Boolean = {
        def checkExpanded() : Boolean = { 
          if(userInterest.category == "Religion"){
            throw InvalidPolicyAccessException(s"Religious Affiliation Can Not Be Shared With Advertisers")
          }else{
            userInterest
          }
        }
        PolicyArguments(checkExpanded _, args)
    }
  }

  case class UserInterest(
    category: String,
    value: String
  )

  type InterestAndReligionPolicy = UserInterestPolicy and ReligionPolicy

  case class Brand(
    id: UUID,
    name: String,
    category: String 
  )

  chaperone class User(
    id: UUID,
    name: String,
    lastname: String, 
    birthdate: Date,
    city: String,
    country: String,
    interests: List[UserInterest] with InterestAndReligionPolicy,
    blackList: List[Brand]
  )

  def getUserInterestForRelevantUsers(@policyParam brand: Brand) : Tuple2[UserId, List[UserInterest]]{
    userService.fetchUsersByInterests(brand.category).map(_ => {
      @policyParam val userId: UserId = _.id
      (userId, _.interests)
    }
  }

  val goodBrand = Brand(UUID.randomUUID(), "goodBrand", "Video Games")
  val badBrand = Brand(UUID.randomUUID(), "badBrand", "Big Bad Something")
  val user1 = User(
    UUID.randomUUID(), 
    "User 1",
    "User 1",
    Date.now(),
    "Boston",
    "US",
    List(UserInterest("The Last of Us", "Video Games")),
    List(badBrand)
  )

  val user2 = User(
    UUID.randomUUID(), 
    "User 2",
    "User 2",
    Date.now(),
    "Helsinki",
    "Finland",
    List(
      UserInterest("The Phantom of The Opera", "Theather"), 
      UserInterest("Atheist", "Religion")
    ),
    List(badBrand)
  )


getUserInterestForRelevantUsers(badBrand)
getUserInterestForRelevantUsers(goodBrand)