;; gorilla-repl.fileformat = 1

;; **
;;; # Gorilla REPL
;;; 
;;; Welcome to gorilla ...
;; **

;; @@
(defn f
  [x y]
  (* 2 x y))
;; @@
;; =>
;;; #'user/f
;; <=

;; **
;;; Some notes could go here.
;; **

;; @@
(f 20 30)
;; @@
;; =>
;;; 1200
;; <=

;; @@
(doall (map println (range 5)))
;; @@
;; ->
;;; 0
;;; 1
;;; 2
;;; 3
;;; 4
;;; 
;; <-
;; =>
;;; (nil nil nil nil nil)
;; <=

;; @@

;; @@
