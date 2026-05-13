import { useEffect, useState } from 'react'
import { toast } from 'react-toastify';
import { type StudentGet, type PassengerPost } from '../Models/Student'
import { type InstructorGet, type InstructorPost } from '../Models/Instructor'
import { type LessonGet, type LessonPost } from '../Models/Lesson'
import * as API from '../Services/APIService';
import StudentList from '../Components/CardLists/StudentList';
import LessonList from '../Components/CardLists/LessonList';
import axios from "axios"

const TestPage = () => {
    const [studentValues, setStudentValues] = useState<StudentGet[]>([]);
    const [instructorValues, setInstructorValues] = useState<InstructorGet[]>([]);
    const [lessonValues, setLessonValues] = useState<LessonGet[]>([]);
    const [Loading, setLoading] = useState<Boolean>();

    useEffect(() => {
        setLoading(true)
        getStudents();
        getInstructors()
        getLessons()
    }, [])

    const getStudents = () => {
        API.getStudentsAPI()
            .then((res : any) => {
                if(res?.data) {
                    setStudentValues(res?.data);
                }
                setLoading(false)
            })
            .catch(() => {
                toast.warning("Could not fetch students!")
                setLoading(false)
            })
    }

    const onStudentDelete = (e:any) => {
        e.preventDefault();
        API.deleteStudentAPI(e.target[0].value)
            .then((res) => {
                if (res?.status === 200)
                {
                    toast.success("Student deleted")
                    getStudents();
                }
            })
            .catch(() => {
                toast.warning("Error while deleting student")
            })
    }

    const getInstructors = () => {
        API.getInstructorsAPI()
            .then((res : any) => {
                if(res?.data) {
                    setInstructorValues(res?.data);
                }
                setLoading(false)
            })
            .catch(() => {
                toast.warning("Could not fetch instructors!")
                setLoading(false)
            })
    }

    const onInstructorDelete = (e:any) => {
        e.preventDefault();
        API.deleteInstructorAPI(e.target[0].value)
            .then((res) => {
                if (res?.status === 200)
                {
                    toast.success("Instructor deleted")
                    getInstructors();
                }
            })
            .catch(() => {
                toast.warning("Error while deleting instructor")
            })
    }

    const getLessons = () => {
        API.getLessonsAPI()
            .then((res : any) => {
                if(res?.data) {
                    setLessonValues(res?.data);
                }
                setLoading(false)
            })
            .catch(() => {
                toast.warning("Could not fetch lessons!")
                setLoading(false)
            })
    }

    const onLessonDelete = (e:any) => {
        e.preventDefault();
        API.deleteLessonAPI(e.target[0].value)
            .then((res) => {
                if (res?.status === 200)
                {
                    toast.success("Lesson deleted")
                    getLessons();
                }
            })
            .catch(() => {
                toast.warning("Error while deleting lesson")
            })
    }

    return (
        <div className>
            {!Loading ? (
                <div>
                    <StudentList Students={studentValues} onDelete={onStudentDelete}/>
                    <StudentList Students={instructorValues} onDelete={onInstructorDelete}/>
                    <LessonList Lessons={lessonValues} onDelete={onLessonDelete}/>
                </div>
            ) : (
                <div>
                    <h2>No students found (Admin Page).</h2>
                </div>
            )
            }
        </div>
    )
}

export default TestPage