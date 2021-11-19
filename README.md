# modular [![GitHub Actions status |pink-gorilla/modular](https://github.com/pink-gorilla/modular/workflows/CI/badge.svg)](https://github.com/pink-gorilla/modular/actions?workflow=CI)[![Clojars Project](https://img.shields.io/clojars/v/org.pinkgorilla/modular.svg)](https://clojars.org/org.pinkgorilla/modular)


Modular helps building modular applications.
Applications that can be extended with modules.

contains:

- config management (allow multiple modules to share a common configuration)
  writes currently used config to a file (which gets updated whenever the config changes)
  config files are edn files OR a clojure datastructure (in the repl)
  edn config files can contain symbols
  supports cprop style app configuration

  since we support symbols in edn files, we have a way to load namespaces

  config state edn fils are HUMAN readable.
  
- persistence (save txt/edn/json) 
  we add serialization for java/localdatetime 

- exploration
  filesystem exploration
  watch for changes in folders

- id
  uuid

## webly/goldly integration

webly uses modular to send the entire configuration to the browser. This works with dynamic config changes.

Goldly provides sci functions to access the configuration.

## unit test  

`clj -M:test`