- ### StaticWiring
  EntryWheel, Plugboard, and Reflector may all be able to be compressed 
to a single superclass (WirePanel) which provides a single array 
holding the which letter (index) points to which letter (contents, int).
  EntryWheel requires final setting at beginning; Plugboard allows changes 
during, and Reflector may not be possible since it may require changes 
internally.

- ### Reflector
  - Did not yet program the rotatable, stepping and rewirable functions.  
    This has been delayed until the mechanics of the reflector in those 
    circumstances can be determined.
  - If the reflector is rewirable, then a method is needed to return the 
    internal wiring.

- ### Plugboard
  - Review PlugboardTest for Exceptions, to be converted to BadKeyExceptions.
  - Add methods for allowing multiple wires added at once.

- ### All classes in machine_pieces
  - Remove AlphabetConstructor as a parameter?  It can be gotten via a method call.
  - Modify to allow letters as input; can use AlphabetConstructor.

- Allow backspace in machine (reset one step back).  Affects rotors.  (Stepping reflector?)

- ### MachineBuilder
  - Written for 3 rotors; add metadata to WiringData to allow variability.
  - Expand methods for each element.
  - After [All classes in machine_pieces], modify to allow letter input.
  - Rewrite build() method for better options.