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