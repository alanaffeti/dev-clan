<?php
namespace App\Controller;

use App\Entity\User;
use App\Form\UserType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use App\Repository\UserRepository;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\SerializerInterface;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\HttpFoundation\Response;
use Doctrine\Persistence\ManagerRegistry;
class RegistrationController extends AbstractController
{
    private $passwordEncoder;

    public function __construct(UserPasswordEncoderInterface $passwordEncoder)
    {
        $this->passwordEncoder = $passwordEncoder;
    }

    /**
     * @Route("/registration", name="registration")
     */
    public function index(Request $request)
    {
        $user = new User();

        $form = $this->createForm(UserType::class, $user);

        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            // Encode the new users password
            $user->setPassword($this->passwordEncoder->encodePassword($user, $user->getPassword()));

            // Set their role
            $user->setRoles(['ROLE_USER']);

            // Save
            $em = $this->getDoctrine()->getManager();
            $em->persist($user);
            $em->flush();

            return $this->redirectToRoute('app_login');
        }

        return $this->render('registration/register.html.twig', [
            'form' => $form->createView(),
        ]);
    }
  
   

    #[Route('/profile', name: 'user_profile')]
    public function userProfile(): Response
    {
        $this->denyAccessUnlessGranted('ROLE_USER');
        // Récupérer l'utilisateur connecté
        $user = $this->getUser();

        return $this->render('profile.html.twig', [
            'user' => $user,
        ]);
    }
    #[Route('/admin_profile', name: 'admin_profile')]
    public function adminProfile(): Response
    {
        $this->denyAccessUnlessGranted('ROLE_ADMIN');
        // Récupérer l'utilisateur connecté
        $user = $this->getUser();

        return $this->render('profileA.html.twig', [
            'user' => $user,

        ]);
    }

    #[Route('/profile/{id}', name: 'user_profile_edit', methods: ['GET', 'POST'])]
    public function editUserProfile(Request $request, user $user, UserRepository $UserRepository): Response
    {
        $this->denyAccessUnlessGranted('ROLE_USER');
        $form = $this->createForm(UserType::class, $user);
        $form->handleRequest($request);

        if ($form->isSubmitted()) {
            $UserRepository->save($user, true);

            return $this->redirectToRoute('user_profile', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('profile_edit.html.twig', [
            'user' => $user,
            'form' => $form,
        ]);
    }

    #[Route('/admin_profile/{id}', name: 'admin_profile_edit', methods: ['GET', 'POST'])]
    public function editAdminProfile(Request $request, user $user, UserRepository $UserRepository): Response
    {
        $this->denyAccessUnlessGranted('ROLE_ADMIN');
        $form = $this->createForm(UserType::class, $user);
        $form->handleRequest($request);

        if ($form->isSubmitted()) {
            $UserRepository->save($user, true);

            return $this->redirectToRoute('display_admin', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('profile.edit.html.twig', [
            'user' => $user,
            'form' => $form,
        ]);
    }
    



    //  #[Route("/AllUsers", name: "User")]
    //  public function getUsers(UserRepository $repo, SerializerInterface $serializer)
    //  {
    //      $users = $repo->findAll();
    //      $json = $serializer->serialize($users, 'json', ['groups' => "users"]);
    //      return new Response($json);
    //  }
    

    // /** 
    //  * #[Route("/adduser/new", name:"addUser")]
    //  */
    // public function addUser(Request $req, NormalizerInterface $normalizer)
    // {
    //     $em = $this->getDoctrine()->getManager();
    //     $users = new User();
    //     $users->setsetName($req->get('name'));
    //     $users->setLastname($req->get('lastname'));
    //     $users->setEmail($req->get('email'));
    //     $users->setRoles($req->get('Role'));
    //     $users->setPassword($req->get('password'));
    //     $em->persist(users);
    //     $em->flush();

    //     $jsonContent = $normalizer->normalize($users,'json', ['groups' => 'accident']);

    //     return new Response(json_encode($jsonContent));

    // }
  
}