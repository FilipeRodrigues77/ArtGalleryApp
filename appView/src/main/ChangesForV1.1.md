## Improvements to consider in the new App version :

1. **Refactoring Repeated Code for Error Handling:**
    - Identify classes with similar error-handling code.
    - Extract common error-handling logic into a generic `Error` class.
    - The `Error` class should accept messages and optional scene information.
    - Update existing error-handling instances to use the new `Error` class.


2. **Consolidating Methods in a Utility Class:**
    - Identify methods in different classes with similar functionality.
    - Create a new utility class to house these common methods.
    - Refactor existing classes to delegate calls to the utility class where applicable.
    - Ensure that the utility class is easily extensible for future shared functionality.


3. **Dynamic Element Resizing with Scene:**
    - Establish a relationship between scene size and element size.
    - Create a mechanism to dynamically adjust element size based on the scene's dimensions.
    - Implement listeners or observers to detect scene size changes.
    - Update elements proportionally when the scene is resized to maintain a consistent layout.


4. **Preserving Filtered Search State:**
    - Enhance the navigation system to remember the state of filtered searches.
    - When viewing details, store the current filters or search criteria.
    - Develop a mechanism to return to the exact filtered scene after viewing details.
    - Ensure that the back navigation logic is aware of the context and restores the appropriate state.


5. **Implementing a Unified Object Search Functionality**
   - When users utilise the search bar, the result list will dynamically adapt to the 
    current scene, aligning with the specific data type associated with that scene. 
    For instance, if users are navigating the Artist page and employ the search bar, 
    the filtered results will exclusively comprise artist-related data.
    Addressing this challenge involves the implementation of a versatile method capable 
    of handling searches across all tables within our database. This generic approach 
    ensures seamless and consistent search functionality, irrespective of the specific 
    data type or scene, offering a unified and efficient experience for users.