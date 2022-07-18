# Modularization

## Benefits

* Can result in faster compile times
    * Independent modules that are not changed or rely on changes can skip
    compiling again
* Can enforce Architecture
* Can have pure Kotlin only modules and not bring in any Android complexity when its not needed
* Increased reusability
* More decoupled code
* Allows for fine-grained Gradle tasks on a subset of code

## Drawbacks

* Overhead for creating new modules
    * Recommend using an IDE module template plugin
* Need to be more careful with defining boundaries. Poorly defined boundaries can result in more issues
    * Can make code harder to find
    * Can lead to cumbersome APIs
    * Can lead to circular dependencies
    * Might require re-defining the module and time is wasted on overhead
* Dependency injection frameworks like Dagger 2 get more complicated
* Need to manage "core" code that is shared among N many modules

## Future Plans

The current way things are broken up are by Clean Architecture layers. This helps enforce the architecture layer boundaries well, and also supports pure Kotlin modules over Android library modules. However, it really limits the benefits of modularization as the app scales.

I would like to refactor this and try to split modules by architecture layer and feature layer. For example on the store feature: `stores-ui`, `stores-domain`, `stores-data`. I would also like for features to not depend on other features directly, as that can lead to longer recompiling from changes and circular dependencies. One way I would like to explore is the pattern of having "api" modules. These are just public interfaces that define contracts for other features to depend on. The root application provides the concrete implementation.

Dagger setup will have to use component dependencies instead of sub components. Modules can define their own component and used when needed. The root application module will be the one managing these components.
