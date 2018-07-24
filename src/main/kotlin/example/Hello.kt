@file:Suppress("MemberVisibilityCanBePrivate")

package example

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

// This kind of definition seems have issue
// <https://stackoverflow.com/questions/51491615/change-the-value-of-a-property-of-some-data-but-tableview-doesnt-update>
//class User(id: Int, name: String) {
//    val idProperty = SimpleIntegerProperty(id)
//    var id by idProperty
//
//    val nameProperty = SimpleStringProperty(name)
//    var name by nameProperty
//}

class User(id: Int, name: String) {
    val id = SimpleIntegerProperty(id)
    val name = SimpleStringProperty(name)
}

private val data = listOf(User(111, "AAA"), User(222, "BBB"), User(333, "CCC"), User(444, "DDD")).observable()

class HelloWorld : View() {

    override val root = vbox {
        tableview(data) {
            column("id", User::id).minWidth(80)
            column("name", User::name).minWidth(200)
        }
    }

}

class HelloWorldStyle : Stylesheet() {
    init {
        root {
            prefWidth = 600.px
            prefHeight = 400.px
        }
    }
}

class HelloWorldApp : App(HelloWorld::class, HelloWorldStyle::class)

fun main(args: Array<String>) {
    launch<HelloWorldApp>()
}