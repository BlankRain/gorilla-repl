;;;; This file is part of gorilla-repl. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; gorilla-repl is licenced to you under the MIT licence. See the file LICENCE.txt for full details.

(ns gorilla-repl.render-values-mw
  (:require [clojure.tools.nrepl.transport :as transport]
            [clojure.tools.nrepl.middleware :as middleware]
            [gorilla-repl.renderer :as renderer]
            [cheshire.core :as json])
  (:import clojure.tools.nrepl.transport.Transport))

;; There's absolutely no way I would have figured this out without referring to
;; https://github.com/clojure/tools.nrepl/blob/master/src/main/clojure/clojure/tools/nrepl/middleware/pr_values.clj
;; and as a result the structure of this follows that code rather closely (which is a fancy way of saying I copied it).

;; This middleware function calls the gorilla-repl render protocol on the value that results from the evaluation, and
;; then converts the result to json.
(defn render-values
  [handler]
  (fn [{:keys [op ^Transport transport] :as msg}]
    (handler (assoc msg :transport (reify Transport
                                     (recv [this] (.recv transport))
                                     (recv [this timeout] (.recv transport timeout))
                                     (send [this resp]
                                       (.send transport
                                              (if-let [[_ v] (find resp :value)]
                                                (assoc resp :value (json/generate-string (renderer/render v)))
                                                resp))
                                       this))))))


;; Unfortunately nREPL's interruptible-eval middleware has a fixed dependency on the pr-values middleware. So here,
;; what we do is fudge the :requires and :expects values to ensure that our rendering middleware gets inserted into
;; the linearized middlware stack between the eval middleware and the pr-values middleware. A bit of a hack!
(middleware/set-descriptor! #'render-values
                            {:requires #{#'clojure.tools.nrepl.middleware.pr-values/pr-values}
                             :expects  #{"eval"}
                             :handles  {}})