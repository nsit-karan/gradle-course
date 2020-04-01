import groovy.transform.*
println "Hello started groovy"

/*
 * groovy is optionally type,i.e, you can either follow static or dynamic typing. This is illustrated below the strings are initialized
 * using 3 different ways (defining the type, using def and not specyfing anything)
 */
String name = "Harry Potter"
def place = "hogwarts"
friend = "ron"

println "hi $name"
println "located at $place"
println "friend with $friend"

/*
 * Classes in groovy:
 * - No need to specify public/private. Following is the semantics followed:
     - attributes are private by default
     - methods are public by default
     - classes are public by default
 * - No need to define getter/setter
 *   - groovy autogenerates this for us and can be directly used
 * 
 * But, the above can be overriden by specifying public/private explicitly
 *
 *
 * @ToString, @EqualsAndHashCode, @TupleConstructor : This triggers a AST transformation (abstract syntax tree)
 * Note : this happens at compile time itself
 * The above 3 are canned and available via @Canonical which will make this even more concise
 */
@ToString(includeNames=true)
@EqualsAndHashCode
@TupleConstructor
class Person {
    String first
    String last

    /*
     * the last evaluated expression is returned automatically. So, no need
     * for the return keyword
     */
    String myToString() { "$first $last"}
}

Person p = new Person()
p.setFirst('Tom')
p.setLast('Riddle')

/*
 * This looks like its violating the semantics of groovy since the attributes are private.
 * But, this statement actually is interpreted by groovy to use the setter itself and is taken
 * care of
 */
p.last = 'marvolo'
println "${p.getFirst()}"
println p.toString()
println p.getFirst()

/*
 * An alternate way of setting the attributes is covered below
 * The below line actually translates to the following:
 * - Use the default constructor (no args)
 * - Internally call setFirst() and setLast() and set the attributes
 *
 * The above is how a simple one liner instantiates the object (indirectly calling the setter method all along)
 */
Person p1 = new Person(first: 'hermione', last: 'granger')
Person p2 = new Person(first: 'hermione', last: 'granger')

/*
 * This one works because @TupleConstructor would have generated the constructor
 * which is based out of the clas attributes
 */
Person p3 = new Person('hermione', 'granger')
println p1 == p2

// Size will be 1 since p1 == p2 since first/last are the same
Set persons = [p1, p2, p3]
println persons.size()

/*
 * Collections start here
 */
def nums = [1,2,3,4] as LinkedList
println nums

def nums1 = [1,2,3,4,1,1] as Set
println nums1

// Iterating over collections
for (int i = 0; i < nums.size(); i++) { println nums[i] }

println ""
for (Integer i : nums) { println i }

println ""
for (i in nums) { println i }

/*
 * This one uses closures (which is a proper class in groovy)
 * This closure has one argument (by default closure has one argument) 
 * and the default variable name is 'it'
 *
 * The second approach uses a var 'n' (could be anything) and then uses it
 * using the '->' operator
 *
 */
println "using closures:"
nums = [3, 1, 4, 1, 5, 9]
nums.each { println it }
nums.each { n -> println n }

/*
 * Another method which gives the index as well as the index in the
 * collection
 */
nums.eachWithIndex { n, idx ->
    println "nums[$idx] = $n"
}

/*
 * '<<' on collections appends the value to the collection
 * this isn't really nice since it allows to modify a variable
 * outside the closure. So, this can't be parallelized
 */
def doubles = []
nums.each {doubles << it * 2}
println doubles

/*
 * Alternate to the above is to use 'collect'
 * which won't have side-effect (no modification of vars)
 * this generates a new collection by applying a closure
 * to each element
 */
println nums.collect { it * 2}

// SImilar eg run on strings
def cities = ['Boston', 'London', 'Delhi']
println cities.collect { it.toLowerCase() }

/*
 * We can chain this out and add a filter also
 * which accepts a closure
 */
println nums.collect { it * 2 }
            .findAll { it % 3 == 0 }


// We can chain this further
println nums.collect { it * 2}
            .findAll { it % 3 == 0 }
            .sum()


/*
 * All about MAPS now
 */
def map = [a:1, b:2, c:3]
println map
println map.keySet()
println map.values()
println map.entrySet()

// add a key
map.d = 4
map['e'] = 5
map.put('f', 6)
println map

// e is a entry using which both key and value can be retrieved
map.each { e -> println "map[${e.key}] = ${e.value}" }

println "Alternate way to iterate over the map"
// alternate way
map.each { k,v -> println "map[$k] = $v" }

// Try this out : the output is basically [a, bb, ccc, ddd, eeee, fffff]
println map.collect { k,v -> k*v }

/*
 * Conditionals in groovy
 */
