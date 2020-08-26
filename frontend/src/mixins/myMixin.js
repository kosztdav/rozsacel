import api from "../api/api";
import { mapActions } from "vuex";

export default {
    methods: {
        ...mapActions([
            "setWorkPlaces",
            "setAllWorkPlaces",
        ]),
        getWorkPlaces() {
            var places = new Array();
            api
                .getAllWorkPlaces()
                .then((response) => {
                    response.data.forEach((place) => {
                        if (place.active) {
                            places.push(place);
                        }
                    });
                    this.setWorkPlaces({ places });
                    this.setAllWorkPlaces({ places: response.data });
                })
                .catch((error) => {
                    console.log(error);
                });
        },
    },
    computed: {
        isMobile() {
            if (
                /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(
                    navigator.userAgent
                )
            ) {
                return true;
            } else {
                return false;
            }
        },
    }
}

