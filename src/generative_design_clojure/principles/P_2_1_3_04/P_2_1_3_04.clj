; P_2_1_3_04.clj
;
; Licensed under the Apache License, Version 2.0

; changing positions of stapled circles in a grid
;
; MOUSE
; position x      : module detail
; position y      : moduel parameter
;
; KEYS
; 1-3             : draw mode
; arrow left/right: number of tiles horizontally
; arrow up/down   : number of tiles vertically
; s               : save png

(ns generative-design-clojure.principles.P_2_1_3_04.P_2_1_3_04
  (:use quil.core
        quil.helpers.seqs
        [quil.applet :only [current-applet]])
  (:import java.util.Calendar
           generativedesign.GenerativeDesign))

(defn setup []
  (set-state!
    :tile-count-x (atom 6.0)
    :tile-count-y (atom 6.0)
    :count (atom 0)
    :draw-mode (atom 1)))

(defn draw []
  (color-mode :hsb 360 100 100)
  (rect-mode :center)
  (smooth)
  (stroke 0)
  (no-fill)
  (background 360)

  (let [count (+ (/ (mouse-x) 10) 10),
        para (float (/ (mouse-y) (height)))]

    (doseq [grid-y (range-incl @(state :tile-count-y)),
            grid-x (range-incl @(state :tile-count-x))
            :let [tile-width (/ (width) @(state :tile-count-x)),
                  tile-height (/ (width) @(state :tile-count-y)),
                  pos-x (+ (* tile-width grid-x) (/ tile-width 2)),
                  pos-y (+ (* tile-height grid-y) (/ tile-height 2))]]
      (push-matrix)
      (translate pos-x pos-y)
      
      ; switch between modules
      (case @(state :draw-mode)
        1 (doseq [i (range count)]
            (rect 0 0 tile-width tile-height)
            (scale (- 1 (/ 3.0 count)))
            (rotate (* para 0.1)))
        2 (doseq [i (range-incl count),
                  :let [gradient (lerp-color (color 0) (color 52 100 71) (/ i count))]]
            (no-stroke)
            (fill gradient (* (/ i count) 200))
            (rotate (/ PI 4))
            (rect 0 0 tile-width tile-height)
            (scale (- 1 (/ 3.0 count)))
            (rotate (* para 1.5)))
        3 (do (color-mode :rgb 255)
            (doseq [i (range-incl count),
                  :let [gradient (lerp-color (color 0 130 164) (color 255) (/ i count))]]
              (no-stroke)
              (fill gradient 170)
              (push-matrix)
              (translate (* 4 i) 0)
              (ellipse 0 0 (/ tile-width 4) (/ tile-height 4))
              (pop-matrix)
              (scale (- 1 (/ 1.5 count)))
              (rotate (* para 1.5))))
        nil)
      (pop-matrix))))

(defn timestamp []
  (let [now (Calendar/getInstance)]
    (format "%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS" now)))

(defn key-release []
  (case (str (raw-key))
    ("s" "S") (save-frame (str (timestamp) "_##.png"))
    "1" (reset! (state :draw-mode) 1)
    "2" (reset! (state :draw-mode) 2)
    "3" (reset! (state :draw-mode) 3)
    nil))

(defn key-press []
  (case (key-as-keyword)
    :down (reset! (state :tile-count-y) (max (dec @(state :tile-count-y)) 1))
    :up (swap! (state :tile-count-y) inc)
    :left (reset! (state :tile-count-x) (max (dec @(state :tile-count-x)) 1))
    :right (swap! (state :tile-count-x) inc)
    nil))

(defsketch P_2_1_3_04
           :title "P_2_1_3_04"
           :setup setup
           :draw draw
           :key-released key-release
           :key-pressed key-press
           :size [550 550])
