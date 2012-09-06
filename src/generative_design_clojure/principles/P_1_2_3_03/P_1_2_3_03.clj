; P_1_2_3_03.clj
;
; Licensed under the Apache License, Version 2.0

; generates a specific color palette and some random "rect-tilings"
;
; MOUSE
; left click      : new composition
;
; KEYS
; s               : save png
; c               : save color palette

(ns generative-design-clojure.principles.P_1_2_3_03.P_1_2_3_03
  (:use quil.core
        quil.helpers.seqs
        [quil.applet :only [current-applet]])
  (:import java.util.Calendar
           generativedesign.GenerativeDesign))

(defn setup []
  (color-mode :hsb 360 100 100 100)
  (no-stroke)
  (set-state!
    :hue-values (atom [])
    :saturation-values (atom [])
    :brightness-values (atom [])
    :color-count (atom 20)
    :alpha-value (atom 27)
    :act-random-seed (atom 0)))

(defn draw []
  (background 0 0 0)
  (random-seed @(state :act-random-seed))
  ; ----- colors -----
  ; create palette
  (let [hue-values (atom []),
        saturation-values (atom []),
        brightness-values (atom [])]
    (dotimes [i @(state :color-count)]
      (if (even? i)
        (do
          (swap! hue-values conj (int (random 0 360)))
          (swap! saturation-values conj 100)
          (swap! brightness-values conj (int (random 0 100))))
        (do
          (swap! hue-values conj 195)
          (swap! saturation-values conj (int (random 0 100)))
          (swap! brightness-values conj 100))))
    (reset! (state :hue-values) @hue-values)
    (reset! (state :saturation-values) @saturation-values)
    (reset! (state :brightness-values) @brightness-values))

  ; ---- area tiling ----
  (let [counter (atom 0), ; count tiles
        row-count (int (random 5 30)), ; row count
        row-height (/ (float (height)) row-count)] ; row height
    (doseq [i (range row-count)
            :let [part-count (atom (+ i 1)),
                  parts (atom []),
                  sum-parts-total (atom 0),
                  sum-parts-now (atom 0)]]
      ; separate each line in parts
      ; how many fragments
      (dotimes [ii @part-count]
        ; sub fragments or not?
        (if (< (random 1.0) 0.075)
          ; take care of big values
          (let [fragments (int (random 2 20))]
            (swap! part-count + fragments)
            (dotimes [iii (+ fragments 1)]
              (swap! parts conj (random 2))))
          (swap! parts conj (random 2 20))))

      ; add all subparts
      (dotimes [ii @part-count]
        (swap! sum-parts-total + (@parts ii)))

      ; draw rects
      (doseq [ii (range (count @parts))
              :let [index (mod @counter @(state :color-count)),
                    x (map-range @sum-parts-now 0 @sum-parts-total 0 (width)),
                    y (* row-height i),
                    w (* (map-range (@parts ii) 0 @sum-parts-total 0 (width)) 1),
                    h (* row-height 1.5)]]
        (swap! sum-parts-now + (@parts ii))

        (begin-shape)
        (fill 0 0 0)
        (vertex x y)
        (vertex (+ x w) y)
        (fill (@(state :hue-values) index)
              (@(state :saturation-values) index)
              (@(state :brightness-values) index)
              @(state :alpha-value))
        (vertex (+ x w) (+ y h))
        (vertex x (+ y h))
        (end-shape :close)

        (swap! counter inc)))))

(defn timestamp []
  (let [now (Calendar/getInstance)]
    (format "%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS" now)))

(defn mouse-release []
  (reset! (state :act-random-seed) (int (random 100000))))

(defn key-release []
  (case (str (raw-key))
    ("s" "S") (save-frame (str (timestamp) "_##.png"))
    ("c" "C") (let [colors (atom [])]
                (doseq [[i hue] (indexed-range(@(state :color-count)))]
                  (swap! colors conj (color 
                                       (@(state :hue-values) i)
                                       (@(state :saturation-values) i)
                                       (@(state :brightness-values) i))))
                (GenerativeDesign/saveASE
                  (current-applet) (int-array @colors) (str (timestamp) ".ase")))
    nil
  ))

(defsketch P_1_2_3_03
           :title "P_1_2_3_03"
           :setup setup
           :draw draw
           :key-released key-release
           :mouse-released mouse-release
           :size [800 800]
           :renderer :opengl)
