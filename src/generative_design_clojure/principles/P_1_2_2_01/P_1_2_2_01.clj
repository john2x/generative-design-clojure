; P_1_2_2_01.clj
;
; Licensed under the Apache License, Version 2.0

; shows how to interpolate colors in different styles/ color modes
;
; MOUSE
; position x    : resolution
;
; KEYS
; 1-3           : load different images
; 4             : no color sorting
; 5             : sort colors on hue
; 6             : sort colors on saturation
; 7             : sort colors on brightness
; 8             : sort colors on grayscale (lumincance)
; s             : save png

(ns generative-design-clojure.principles.P_1_2_2_01.P_1_2_2_01
  (:use quil.core)
  (:import java.util.Calendar))

(declare shake-colors)

(defn setup []
  (color-mode :hsb 360 100 100 100)
  (no-stroke)
  (no-cursor)
  (set-state!
    :img (atom (load-image "data/pic1.jpg"))
    :sort-mode (atom nil)
    :colrs (atom []))
  (no-stroke)
  (shake-colors))

(defn draw []
  (let [tile-count (/ (width) (max (mouse-y) 5)),
        rect-size (/ (width) (float tile-count)),
        inter-col (atom (color 0))]

    (doseq [grid-y (range tile-count-y)
            :let [col1 (colors-left grid-y),
                  col2 (colors-right grid-y)]]

      (doseq [grid-x (range tile-count-x)
              :let [amount (map-range grid-x 0 (- tile-count-x 1) 0 1)]]

        (if @(state :interpolate-shortest)
          (do
            ; switch to rgb
            (color-mode :rgb 255 255 255 255)
            (reset! (state :inter-col) (lerp-color col1 col2 amount))
            ; switch back
            (color-mode :hsb 360 100 100 100))
          ; else
          (reset! (state :inter-col) (lerp-color col1 col2 amount)))

        (fill @(state :inter-col))
        (rect (* tile-width grid-x) (* tile-height grid-y) tile-width tile-height)))))

(defn key-release []
  (case (str (raw-key))
    "1" (reset! (state :interpolate-shortest) true)
    "2" (reset! (state :interpolate-shortest) false)
    nil))

(defn mouse-release []
  (shake-colors))

(defn timestamp []
  (let [now (Calendar/getInstance)]
    (format "%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS" now)))

(defn key-press []
  (if (= (str (raw-key)) "s")
    (save-frame (str (timestamp) "_##.png"))))

(defn shake-colors []
  (let [colors-left (atom []),
        colors-right (atom [])]

    (doseq [i (range 10)] ; always have 10 colors
      (swap! colors-left conj (color (random 0 60) (random 0 100) 100))
      (swap! colors-right conj (color (random 160 190) 100 (random 0 100))))

    (reset! (state :colors-left) @colors-left)
    (reset! (state :colors-right) @colors-right)))

(defsketch P_1_2_2_01
           :title "P_1_2_2_01"
           :setup setup
           :draw draw
           :key-released key-release
           :mouse-released mouse-release
           :size [600 600])
