import com.google.inject.{AbstractModule, Guice, ImplementedBy, Provides}
import javax.inject.{Inject, Singleton}

trait Material

class Carbon extends Material

class Vibranium extends Material


trait Sword {
  def swing
}

class WoodenSword extends Sword {
  def swing = println("나무로 휘두름")
}

class PowerSword @Inject()(material: Material) extends Sword {
  def swing = {
    material match {
      case _: Carbon => println("CCCCCCCarbon Swing")
      case _: Vibranium => println("I can do this all day")
    }
  }
}


trait Staff {
  def cast
}

class PaperStaff extends Staff {
  def cast: Unit = println("a...b......c.........d")
}

class SuperUltraStaff extends Staff {
  def cast: Unit = println("FIRE~~~~~~~")
}


class Hero @Inject()(sword: Sword, staff: Staff) {
  def cry = {
    sword.swing
    staff.cast
  }
}

@Singleton
class Ring {
  def polish = println("블링블링")
}


class Module extends AbstractModule {
  override def configure(): Unit = {

    bind(classOf[Material]).to(classOf[Vibranium])
    bind(classOf[Sword]).to(classOf[PowerSword])
    bind(classOf[Staff]).to(classOf[SuperUltraStaff])

  }
}

object Main extends App {

  val injector = Guice.createInjector(new Module)
  val hero = injector.getInstance(classOf[Hero])
  hero.cry

  val ring = injector.getInstance(classOf[Ring])
  ring.polish

}

