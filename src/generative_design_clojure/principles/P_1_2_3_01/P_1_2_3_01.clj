; P_1_2_3_01.clj
;
; Licensed under the Apache License, Version 2.0

; generates specific color palettes
;
; MOUSE
; position x/y    : row and column count
;
; KEYS
; 0-9             : creates specific color palettes
; s               : save png
; c               : save color palette

(ns generative-design-clojure.principles.P_1_2_3_01.P_1_2_3_01
  (:use quil.core
        quil.helpers.seqs
        [quil.applet :only [current-applet]])
  (:import java.util.Calendar
           generativedesign.GenerativeDesign))

(defn random-color-values [hue-fn saturation-fn brightness-fn]
  (let [hue-values (atom []),
        saturation-values (atom []),
        brightness-values (atom [])]
    (doseq [i (range @(state :tile-count-x))]
      (swap! hue-values conj (eval hue-fn))
      (swap! saturation-values conj (eval saturation-fn))
      (swap! brightness-values conj (eval brightness-fn)))

    [@hue-values @saturation-values @brightness-values]))

(defn random-color-values-2 
  [hue-fn-1 saturation-fn-1 brightness-fn-1,
   hue-fn-2 saturation-fn-2 brightness-fn-2]
  (let [hue-values (atom []),
        saturation-values (atom []),
        brightness-values (atom [])]
    (doseq [i (range @(state :tile-count-x))]
      (if (even? i)
        (do
          (swap! hue-values conj (eval hue-fn-1))
          (swap! saturation-values conj (eval saturation-fn-1))
          (swap! brightness-values conj (eval brightness-fn-1)))
        (do
          (swap! hue-values conj (eval hue-fn-2))
          (swap! saturation-values conj (eval saturation-fn-2))
          (swap! brightness-values conj (eval brightness-fn-2)))))

    [@hue-values @saturation-values @brightness-values]))

(defn setup []
  (color-mode :hsb 360 100 100 100)
  (no-stroke)
  (set-state!
    :hue-values (atom [])
    :saturation-values (atom [])
    :brightness-values (atom [])
    :tile-count-x (atom 50)
    :tile-count-y (atom 10))

  ; init with random values
  (let [[hue-values saturation-values brightness-values]
          (random-color-values
            '(int (quil.core/random 0 360))
            '(int (quil.core/random 0 100))
            '(int (quil.core/random 0 100)))]
    (reset! (state :hue-values) hue-values)
    (reset! (state :saturation-values) saturation-values)
    (reset! (state :brightness-values) brightness-values)))

(defn draw []
  ; whie back
  (background 0 0 100)

  (let [counter (atom 0), ; count every tile
        ; map mouse to grid resolution
        current-tile-count-x (int (map-range
                                    (mouse-x) 0 (width) 1 @(state :tile-count-x))),
        current-tile-count-y (int (map-range
                                    (mouse-y) 0 (height) 1 @(state :tile-count-y))),
        tile-width (/ (width) (float current-tile-count-x)),
        tile-height (/ (height) (float current-tile-count-y))]
    (doseq [grid-y (range @(state :tile-count-y)),
            grid-x (range @(state :tile-count-x)),
            :let [pos-x (* tile-width grid-x),
                  pos-y (* tile-height grid-y),
                  index (mod @counter current-tile-count-x)]]
      ; get component color values
      (fill
        (@(state :hue-values) index)
        (@(state :saturation-values) index)
        (@(state :brightness-values) index))
      (rect pos-x pos-y tile-width tile-height)
      (swap! counter inc)
    )
  ))

(defn timestamp []
  (let [now (Calendar/getInstance)]
    (format "%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS" now)))

(defn key-release []
  (case (str (raw-key))
    ("s" "S") (save-frame (str (timestamp) "_##.png"))
    ("c" "C") (let [colors (atom [])]
                (doseq [[i hue] (indexed-range(count @(state :hue-values)))]
                  (swap! colors conj (color 
                                       (@(state :hue-values) i)
                                       (@(state :saturation-values) i)
                                       (@(state :brightness-values) i))))
                (GenerativeDesign/saveASE
                  (current-applet) (int-array @colors) (str (timestamp) ".ase")))
    "1" (let [[hue-values saturation-values brightness-values]
                (random-color-values
                  '(int (quil.core/random 0 360))
                  '(int (quil.core/random 0 100))
                  '(int (quil.core/random 0 100)))]
          (reset! (state :hue-values) hue-values)
          (reset! (state :saturation-values) saturation-values)
          (reset! (state :brightness-values) brightness-values))
    "2" (let [[hue-values saturation-values brightness-values]
                (random-color-values
                  '(int (quil.core/random 0 360))
                  '(int (quil.core/random 0 100))
                  '100)]
          (reset! (state :hue-values) hue-values)
          (reset! (state :saturation-values) saturation-values)
          (reset! (state :brightness-values) brightness-values))
    "3" (let [[hue-values saturation-values brightness-values]
                (random-color-values
                  '(int (quil.core/random 0 360))
                  '100
                  '(int (quil.core/random 0 100)))]
          (reset! (state :hue-values) hue-values)
          (reset! (state :saturation-values) saturation-values)
          (reset! (state :brightness-values) brightness-values))
    "4" (let [[hue-values saturation-values brightness-values]
                (random-color-values
                  '0
                  '0
                  '(int (quil.core/random 0 100)))]
          (reset! (state :hue-values) hue-values)
          (reset! (state :saturation-values) saturation-values)
          (reset! (state :brightness-values) brightness-values))
    "5" (let [[hue-values saturation-values brightness-values]
                (random-color-values
                  '195
                  '100
                  '(int (quil.core/random 0 100)))]
          (reset! (state :hue-values) hue-values)
          (reset! (state :saturation-values) saturation-values)
          (reset! (state :brightness-values) brightness-values))
    "6" (let [[hue-values saturation-values brightness-values]
                (random-color-values
                  '195
                  '(int (quil.core/random 0 100))
                  '100)]
          (reset! (state :hue-values) hue-values)
          (reset! (state :saturation-values) saturation-values)
          (reset! (state :brightness-values) brightness-values))
    "7" (let [[hue-values saturation-values brightness-values]
                (random-color-values
                  '(int (quil.core/random 0 180))
                  '(int (quil.core/random 80 100))
                  '(int (quil.core/random 50 90)))]
          (reset! (state :hue-values) hue-values)
          (reset! (state :saturation-values) saturation-values)
          (reset! (state :brightness-values) brightness-values))
    "8" (let [[hue-values saturation-values brightness-values]
                (random-color-values
                  '(int (quil.core/random 180 360))
                  '(int (quil.core/random 80 100))
                  '(int (quil.core/random 50 90)))]
          (reset! (state :hue-values) hue-values)
          (reset! (state :saturation-values) saturation-values)
          (reset! (state :brightness-values) brightness-values))
    "9" (let [[hue-values saturation-values brightness-values]
                (random-color-values-2
                  '(int (quil.core/random 0 360))
                  '100
                  '(int (quil.core/random 0 100))
                  '195
                  '(int (quil.core/random 0 100))
                  '100)]
          (reset! (state :hue-values) hue-values)
          (reset! (state :saturation-values) saturation-values)
          (reset! (state :brightness-values) brightness-values))
    "0" (let [[hue-values saturation-values brightness-values]
                (random-color-values-2
                  '192
                  '(int (quil.core/random 0 100))
                  '(int (quil.core/random 10 100))
                  '273
                  '(int (quil.core/random 0 100))
                  '(int (quil.core/random 10 90)))]
          (reset! (state :hue-values) hue-values)
          (reset! (state :saturation-values) saturation-values)
          (reset! (state :brightness-values) brightness-values))
    nil
  ))

(defsketch P_1_2_3_01
           :title "P_1_2_3_01"
           :setup setup
           :draw draw
           :key-released key-release
           :size [800 800])
