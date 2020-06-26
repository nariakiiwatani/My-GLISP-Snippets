(defn bezier-curve
	{:params [
	  {:label "Start" :type "vec2"}
		{:label "Cp1" :type "vec2"}
		{:label "Cp2" :type "vec2"}
		{:label "End" :type "vec2"}
	]
	:handles{:draw (fn [{:params [s c1 c2 e]
                         :return path}]
                     [
                      {:type "point" :id 0 :pos s}
                      {:type "point" :id 1 :pos c1}
                      {:type "point" :id 2 :pos c2}
                      {:type "point" :id 3 :pos e}
                      {:type "path" :id "handle1" :path (line s c1)}
                      {:type "path" :id "handle2" :path (line e c2)}
											]
									)
             :drag (fn [{:id idx :pos p :params pts}]
                     (replace-nth pts idx p))
	}}
[v1 cp1 cp2 v2]
[:path :M v1 :C cp1 cp2 v2])
