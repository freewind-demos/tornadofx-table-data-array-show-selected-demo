@file:Suppress("MemberVisibilityCanBePrivate")

package example

import javafx.beans.binding.Bindings
import javafx.beans.property.ReadOnlyObjectWrapper
import javafx.beans.property.ReadOnlyProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import tornadofx.*

private val columnNames = listOf("id", "name")
private val data = FXCollections.observableArrayList(listOf(111, "AAA"), listOf(222, "BBB"), listOf(333, "CCC"), listOf(444, "DDD"))

class RowModel(row: List<Any>) : ViewModel() {
    val backingRow = SimpleObjectProperty(row)
}

class HelloWorld : View() {

    private val rowModel = RowModel(emptyList())

    override val root = hbox {
        tableview<List<Any>>(data) {
            columnNames.forEachIndexed { index, name ->
                column<List<Any>, String>(name) { it.value[index].toString().toProperty() }
            }
            rowModel.rebindOnChange(this) { selectedRow ->
                backingRow.value = selectedRow ?: emptyList()
            }
        }
        form {
            fieldset {
                columnNames.forEachIndexed { index, name ->
                    field(name) {
                        textfield(rowModel.backingRow.map { it -> it.getOrNull(index)?.toString() ?: "" })
                    }
                }

            }
        }
    }
}

fun <T, K> ReadOnlyProperty<T>.map(fn: (T) -> K): ReadOnlyProperty<K> {
    val source = this
    return ReadOnlyObjectWrapper<K>().apply {
        source.addListener { _, _, newValue -> this.value = fn(newValue) }
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