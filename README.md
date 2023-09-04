# modular
[![GitHub Actions status |pink-gorilla/modular](https://github.com/pink-gorilla/modular/workflows/CI/badge.svg)](https://github.com/pink-gorilla/modular/actions?workflow=CI)
[![Clojars Project](https://img.shields.io/clojars/v/org.pinkgorilla/modular.svg)](https://clojars.org/org.pinkgorilla/modular)


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

## timbre log config

See example in `demo/logging.clj`

```
clj -X:demo-log-default
clj -X:demo-log-custom
```

## services

Modular uses juxt/clip for a services defintion.

Demo of the services module:

To run all services (and keep running): `clj -X:demo-system` or `clj -M:system`
To run services, invoke a function (then end): `clj -X:demo-system:run :task '[1 2 3]'`
or `clj -M:system 1 2 3`



## webly/goldly integration

webly uses modular to send the entire configuration to the browser. This works with dynamic config changes.

Goldly provides sci functions to access the configuration.

## unit test

clj:
`clj -M:test`

cljs:
```clj -X:webly:npm-install
   clj -X:webly:build-ci
   npm test
```


