(ns generative-design-clojure.core
    (:use quil.core)
    (:require [generative-design-clojure.principles.P_1_0_01.P_1_0_01 :as dynamic]))

(defsketch generative-art-p
                      :title "Generative Design in Clojure"
                      :setup dynamic/setup
                      :draw dynamic/draw
                      :mouse-moved dynamic/mouse-moved
                      :size [720 720])

