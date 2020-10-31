import api from "../api/api";
import {mapActions} from "vuex";

export default {
    data() {
        return {
            userButtons: [
                {
                    text: "Jelenléti ív",

                    url: "/jelenlet",
                    icon: "calendar3",
                },
                {
                    text: "Report",
                    url: "/osszegzes",
                    icon: "newspaper"
                },
                {
                    text: "Beállítások",

                    url: "/beallitasok",
                    icon: "tools",
                },
            ],
            adminButtons: [
                {
                    text: "Jelenléti ív",
                    url: "/jelenletek",
                    icon: "calendar3",
                },
                {
                    text: "Helyszínek",
                    url: "/helyszinek",
                    icon: "building",
                },
                {
                    text: "Dolgozók",
                    url: "/dolgozok",
                    icon: "person-lines-fill",
                },
                {
                    text: "Report",
                    url: "/report",
                    icon: "newspaper"
                },
                {
                    text: "Beállítások",
                    url: "/beallitasok",
                    icon: "tools",
                },
            ],
        }
    },
    methods: {
        ...mapActions([
            "setWorkPlaces",
            "setAllWorkPlaces",
            "setEmployees"
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
                    this.setWorkPlaces({places});
                    this.setAllWorkPlaces({places: response.data});
                })
                .catch((error) => {
                    console.log(error);
                });
        },
        getEmployees() {
            api
                .getAllUsers()
                .then((response) => {
                    var employees = new Array();
                    response.data.forEach((employee) => {
                        if (!employee.role) {
                            employees.push(employee);
                        }
                    });
                    this.setEmployees({employees});
                })
                .catch((error) => {
                    console.log(error);
                });
        }
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

