(defn on-same-line? [A B C]
  (< .99 (abs(vec2/dot (vec2/normalize (vec2/- B A))
                 (vec2/normalize (vec2/- C A))))))

(defn circumcenter [A B C]
  (let [a (vec2/dist B C)
        b (vec2/dist C A)
        c (vec2/dist A B)
        a2 (* a a) b2 (* b b) c2 (* c c)
        a-len (* a2 (+ (- a2) b2 c2))
        b-len (* b2 (+ a2 (- b2) c2))
        c-len (* c2 (+ a2 b2 (- c2)))]
    (vec2/scale
     (vec2/+
      (vec2/scale A a-len)
      (vec2/+ (vec2/scale B b-len) (vec2/scale C c-len)))
     (/ (+ a-len b-len c-len)))))

(defn circumarc
  {:params [{:label "From" :type "vec2"}
            {:label "Via" :type "vec2"}
            {:label "To" :type "vec2"}]
   :handles
   {:draw (fn [{:params pts}]
            (map-indexed #(hash-map :id %0 :type "point" :pos %1) pts))
    :drag (fn [{:id idx :pos p
                :params pts}]
            (replace-nth pts idx p))}}
  [A B C]
  (if (on-same-line? A B C)
    (line A C)
    (let [c (circumcenter A B C)]
      (let [r (vec2/dist c A)

            D (vec2/rotate [0 0] HALF_PI (vec2/- B A))
            turn (if (pos? (vec2/dot D (vec2/- C B))) :cw :ccw)

            start (vec2/angle (vec2/- A c))
            end (vec2/angle (vec2/- C c))
            end (cond (and (= turn :cw) (> start end))
                      (+ end TWO_PI)

                      (and (= turn :ccw) (< start end))
                      (- end TWO_PI)

                      :else end)]
        (arc c r start end)))))

(defvar line-x 133.5)
(defvar line-length 230)
(defvar pos [-7 2])
(defvar leave-y 75.6953125)

(transform (translate pos)
(style (stroke "black" 10 :cap "round")	(circumarc [line-x leave-y] [-130.5 (const 0)] [line-x (- leave-y)])
	(style (stroke *stroke-color* (* *stroke-width* (percent 440)) :cap "square")
	(line [line-x 99.3046875] [line-x -108.6953125]))))
